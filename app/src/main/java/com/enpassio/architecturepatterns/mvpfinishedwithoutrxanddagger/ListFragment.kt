package com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.enpassio.architecturepatterns.R
import com.enpassio.core.data.DataManager
import com.enpassio.core.data.model.CharacterMarvel
import com.enpassio.core.ui.list.ListContract
import com.enpassio.core.ui.list.ListPresenter


class ListFragment : Fragment(), ListContract.ListView, ListAdapter.InteractionListener, SwipeRefreshLayout.OnRefreshListener {

    override fun showCharacters(characterList: List<CharacterMarvel>) {
        if (mListCharacterAdapter?.viewType !== ListAdapter.VIEW_TYPE_GALLERY) {
            mListCharacterAdapter?.removeAll()
            mListCharacterAdapter?.viewType = (ListAdapter.VIEW_TYPE_GALLERY)
        }

        if (!(mSwipeRefreshLayout?.isActivated()!!)) {
            mSwipeRefreshLayout?.setEnabled(true)
        }
        mListCharacterAdapter?.addItems(characterList)
    }

    override fun showSearchedCharacters(searchResults: List<CharacterMarvel>) {
        if (mListCharacterAdapter?.viewType !== ListAdapter.VIEW_TYPE_LIST) {
            mListCharacterAdapter?.removeAll()
            mListCharacterAdapter?.viewType = (ListAdapter.VIEW_TYPE_LIST)

        }
        if (mSwipeRefreshLayout?.isActivated()!!) {
            mSwipeRefreshLayout?.setEnabled(false)
        }
        mListCharacterAdapter?.addItems(searchResults)
    }

    override fun showProgress() {
        if (mListCharacterAdapter?.isEmpty!! && !(mSwipeRefreshLayout?.isRefreshing()!!)) {
            mContentLoadingProgress?.setVisibility(View.VISIBLE)
        }
    }

    override fun hideProgress() {
        mSwipeRefreshLayout?.setRefreshing(false)
        mContentLoadingProgress?.setVisibility(View.GONE)
        mListCharacterAdapter?.removeLoadingView()
    }

    override fun showUnauthorizedError() {
        mMessageImage?.setImageResource(R.drawable.ic_error_list)
        mMessageText?.setText(getString(R.string.error_generic_server_error, "Unauthorized"))
        mMessageButton?.setText(getString(R.string.action_try_again))
        showMessageLayout(true)
    }

    override fun showError(errorMessage: String) {
        mMessageImage?.setImageResource(R.drawable.ic_error_list)
        mMessageText?.setText(getString(R.string.error_generic_server_error, errorMessage))
        mMessageButton?.setText(getString(R.string.action_try_again))
        showMessageLayout(true)
    }

    override fun showEmpty() {
        mMessageImage?.setImageResource(R.drawable.ic_clear)
        mMessageText?.setText(getString(R.string.error_no_items_to_display))
        mMessageButton?.setText(getString(R.string.action_check_again))
        showMessageLayout(true)
    }

    override fun showMessageLayout(show: Boolean) {
        mMessageLayout?.setVisibility(if (show) View.VISIBLE else View.GONE)
        mCharactersRecycler?.setVisibility(if (show) View.GONE else View.VISIBLE)
    }


    override fun onListClick(character: CharacterMarvel, sharedElementView: View, adapterPosition: Int) {
        //start detail activity to show character details
    }

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
        val view = inflater.inflate(R.layout.mvp_finished_without_rx_and_dagger_fragment_list, container, false)

        Log.d("my_tag", "onCreateView called")
        initViews(view)
        mListPresenter!!.attachView(this)
        mListCharacterAdapter!!.setListInteractionListener(this)
        if (mListCharacterAdapter?.isEmpty!!) {
            mListPresenter?.onInitialListRequested()
        }
        return view
    }

    private fun initViews(view: View) {
        Log.d("my_tag", "initViews called")
        mActivity = activity as AppCompatActivity?
        mActivity!!.setSupportActionBar(view.findViewById(R.id.toolbar) as Toolbar)

        mCharactersRecycler = view.findViewById(R.id.recycler_characters) as RecyclerView
        mCharactersRecycler!!.setHasFixedSize(true)
        mCharactersRecycler!!.isMotionEventSplittingEnabled = false
        mCharactersRecycler!!.itemAnimator = DefaultItemAnimator()
        mCharactersRecycler!!.adapter = mListCharacterAdapter

        mCharactersRecycler!!.setLayoutManager(setUpLayoutManager())

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh) as SwipeRefreshLayout
        mSwipeRefreshLayout!!.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
        mSwipeRefreshLayout!!.setColorSchemeResources(R.color.colorAccent)
        mSwipeRefreshLayout!!.setOnRefreshListener(this)

    }

    private fun setUpLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager: RecyclerView.LayoutManager
        Log.d("my_tag", "setUpLayoutManager called")
        layoutManager = initGridLayoutManager(TAB_LAYOUT_SPAN_SIZE, TAB_LAYOUT_ITEM_SPAN_SIZE)
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
        Log.d("my_tag", "initGridLayoutManager called")
        return gridLayoutManager
    }


    override fun onRefresh() {
        Log.d("my_tag", "onRefresh called")
        mListCharacterAdapter!!.removeAll()
        mListPresenter!!.onInitialListRequested()
    }

    override fun onDestroyView() {
        Log.d("my_tag", "onDestroyView called")
        mCharactersRecycler!!.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("my_tag", "onDestroy called")
        mListPresenter!!.detachView()
        super.onDestroy()
    }

    companion object {

        private val TAB_LAYOUT_SPAN_SIZE = 2
        private val TAB_LAYOUT_ITEM_SPAN_SIZE = 1
    }
}