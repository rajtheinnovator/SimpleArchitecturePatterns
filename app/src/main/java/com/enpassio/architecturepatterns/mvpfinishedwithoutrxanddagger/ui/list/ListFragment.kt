package com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.ui.list

import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.text.TextUtils
import android.transition.Slide
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.enpassio.architecturepatterns.R
import com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.util.DisplayMetricsUtil
import com.example.android.core.data.DataManager
import com.example.android.core.data.model.CharacterMarvel
import com.example.android.core.ui.list.ListContract
import com.example.android.core.ui.list.ListPresenter

/**
 * Created by Greta Grigutė on 2018-10-17.
 */


class ListFragment : Fragment(), ListContract.ListView, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private var mActivity: AppCompatActivity? = null
    private var mListPresenter: ListPresenter? = null
    private var mListCharacterAdapter: ListAdapter? = null
    private var mSearchQuery: String? = null

    private var mCharactersRecycler: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mContentLoadingProgress: ProgressBar? = null

    private var mMessageLayout: View? = null
    private var mMessageImage: ImageView? = null
    private var mMessageText: TextView? = null
    private var mMessageButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
        mListPresenter = ListPresenter(DataManager.instance)
        mListCharacterAdapter = ListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.mvp_finished_without_rx_and_dagger_list_fragment, container, false)

        initViews(view)
        mListPresenter!!.attachView(this)
        if (mListCharacterAdapter!!.isEmpty) {
            mListPresenter!!.onInitialListRequested()
        }
        return view
    }

    private fun initViews(view: View) {
        mActivity = activity as AppCompatActivity?

        mCharactersRecycler = view.findViewById(R.id.recycler_characters) as RecyclerView
        mCharactersRecycler!!.setHasFixedSize(true)
        mCharactersRecycler!!.isMotionEventSplittingEnabled = false
        mCharactersRecycler!!.itemAnimator = DefaultItemAnimator()
        mCharactersRecycler!!.adapter = mListCharacterAdapter

        val isTabletLayout = DisplayMetricsUtil.isScreenW(SCREEN_TABLET_DP_WIDTH)
        mCharactersRecycler!!.layoutManager = setUpLayoutManager(isTabletLayout)
        mCharactersRecycler!!.addOnScrollListener(setupScrollListener(isTabletLayout,
               mCharactersRecycler!!.layoutManager))

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh) as SwipeRefreshLayout
        mSwipeRefreshLayout!!.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
        mSwipeRefreshLayout!!.setColorSchemeResources(R.color.colorAccent)
        mSwipeRefreshLayout!!.setOnRefreshListener(this)

        mContentLoadingProgress = view.findViewById(R.id.progress) as ProgressBar
        mMessageLayout = view.findViewById(R.id.message_layout)
        mMessageImage = view.findViewById(R.id.iv_message) as ImageView
        mMessageText = view.findViewById(R.id.tv_message) as TextView
        mMessageButton = view.findViewById(R.id.btn_try_again) as Button
        mMessageButton!!.setOnClickListener { onRefresh() }
    }

    private fun setUpLayoutManager(isTabletLayout: Boolean): RecyclerView.LayoutManager {
        val layoutManager: RecyclerView.LayoutManager
        if (!isTabletLayout) {
            layoutManager = LinearLayoutManager(mActivity)
        } else {
            layoutManager = initGridLayoutManager(TAB_LAYOUT_SPAN_SIZE, TAB_LAYOUT_ITEM_SPAN_SIZE)
        }
        return layoutManager
    }

    private fun initGridLayoutManager(spanCount: Int, itemSpanCount: Int): RecyclerView.LayoutManager {
        val gridLayoutManager = GridLayoutManager(mActivity, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (mListCharacterAdapter!!.getItemViewType(position)) {
                    ListAdapter.VIEW_TYPE_LOADING ->
                        // If it is a loading view we wish to accomplish a single item per row
                        return spanCount
                    else ->
                        // Else, define the number of items per row (considering TAB_LAYOUT_SPAN_SIZE).
                        return itemSpanCount
                }
            }
        }
        return gridLayoutManager
    }

    private fun setupScrollListener(isTabletLayout: Boolean,
                                    layoutManager: RecyclerView.LayoutManager?): EndlessRecyclerViewOnScrollListener {
        return object : EndlessRecyclerViewOnScrollListener(if (isTabletLayout)
            (layoutManager as GridLayoutManager?)!!
        else
            (layoutManager as LinearLayoutManager?)!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if (mListCharacterAdapter!!.addLoadingView()) {
                    mListPresenter!!.onListEndReached(totalItemsCount, null, mSearchQuery)
                }
            }
        }
    }

    override fun onRefresh() {
        mListCharacterAdapter!!.removeAll()
        mListPresenter!!.onInitialListRequested()
    }


    override fun showCharacters(characterList: List<CharacterMarvel>) {
        if (mListCharacterAdapter!!.viewType !== ListAdapter.VIEW_TYPE_GALLERY) {
            mListCharacterAdapter!!.removeAll()
            mListCharacterAdapter!!.viewType = ListAdapter.VIEW_TYPE_GALLERY
        }

        if (!mSwipeRefreshLayout!!.isActivated) {
            mSwipeRefreshLayout!!.isEnabled = true
        }
        mListCharacterAdapter!!.addItems(characterList)
    }

   override fun showSearchedCharacters(searchResults: List<CharacterMarvel>) {
        if (mListCharacterAdapter!!.viewType !== ListAdapter.VIEW_TYPE_LIST) {
            mListCharacterAdapter!!.removeAll()
            mListCharacterAdapter!!.viewType = ListAdapter.VIEW_TYPE_LIST

        }
        if (mSwipeRefreshLayout!!.isActivated) {
            mSwipeRefreshLayout!!.isEnabled = false
        }
        mListCharacterAdapter!!.addItems(searchResults)
    }

   override fun showProgress() {
        if (mListCharacterAdapter!!.isEmpty && !mSwipeRefreshLayout!!.isRefreshing) {
            mContentLoadingProgress!!.visibility = View.VISIBLE
        }
    }

   override fun hideProgress() {
        mSwipeRefreshLayout!!.isRefreshing = false
        mContentLoadingProgress!!.visibility = View.GONE
        mListCharacterAdapter!!.removeLoadingView()
    }

   override fun showUnauthorizedError() {
        mMessageImage!!.setImageResource(R.drawable.ic_error_list)
        mMessageText!!.text = getString(R.string.error_generic_server_error, "Unauthorized")
        mMessageButton!!.text = getString(R.string.action_try_again)
        showMessageLayout(true)
    }

    override fun showError(errorMessage: String) {
        mMessageImage!!.setImageResource(R.drawable.ic_error_list)
        mMessageText!!.text = getString(R.string.error_generic_server_error, errorMessage)
        mMessageButton!!.text = getString(R.string.action_try_again)
        showMessageLayout(true)
    }

    override fun showEmpty() {
        mMessageImage!!.setImageResource(R.drawable.ic_clear)
        mMessageText!!.text = getString(R.string.error_no_items_to_display)
        mMessageButton!!.text = getString(R.string.action_check_again)
        showMessageLayout(true)
    }

    override fun showMessageLayout(show: Boolean) {
        mMessageLayout!!.visibility = if (show) View.VISIBLE else View.GONE
        mCharactersRecycler!!.visibility = if (show) View.GONE else View.VISIBLE
    }


    private fun makeTransitionBundle(sharedElementView: View): Bundle? {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity!!,
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)!!).toBundle()
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(queryText: String): Boolean {
        mSearchQuery = queryText
        if (!TextUtils.isEmpty(mSearchQuery)) {
            mListPresenter!!.onCharacterSearched(mSearchQuery)
            return true
        }
        return false
    }

    override fun onDestroyView() {
        mCharactersRecycler!!.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        mListPresenter!!.detachView()
        super.onDestroy()
    }

    companion object {

        private val TAB_LAYOUT_SPAN_SIZE = 2
        private val TAB_LAYOUT_ITEM_SPAN_SIZE = 1
        private val SCREEN_TABLET_DP_WIDTH = 600

        @JvmOverloads
        fun newInstance(arguments: Bundle? = null): ListFragment {
            val fragment = ListFragment()
            if (arguments != null) {
                fragment.arguments = arguments
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fragment.enterTransition = Slide()
            }
            return fragment
        }
    }
}