package com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.ui.list

import android.support.annotation.IntDef
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.enpassio.architecturepatterns.R
import com.example.android.core.data.model.CharacterMarvel
import com.squareup.picasso.Picasso
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.ArrayList

/**
 * Created by Greta Grigutė on 2018-10-17.
 */
/**
 * [RecyclerView.Adapter] populated with [CharacterMarvel]
 * makes the call to the [ListAdapter.InteractionListener].
 */
class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListInteractionListener: InteractionListener? = null
    private val mCharacterList: MutableList<CharacterMarvel>

    @ViewType
    var viewType: Int = 0

    val isEmpty: Boolean
        get() = itemCount == 0

    @IntDef(VIEW_TYPE_LOADING, VIEW_TYPE_GALLERY, VIEW_TYPE_LIST)
    @Retention(RetentionPolicy.SOURCE)
    annotation class ViewType

    init {
        mCharacterList = ArrayList<CharacterMarvel>()
        viewType = VIEW_TYPE_GALLERY
        mListInteractionListener = null
    }

    override fun getItemViewType(position: Int): Int {
        return if (mCharacterList[position] == null) VIEW_TYPE_LOADING else viewType
    }

    override fun getItemCount(): Int {
        return mCharacterList.size
    }

    override fun getItemId(position: Int): Long {
        return (if (mCharacterList.size >= position) mCharacterList[position].id else -1).toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
            onIndicationViewHolder(parent)
        } else onGenericItemViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_LOADING) {
            return  // no-op
        }
        onBindGenericItemViewHolder(holder as CharacterViewHolder, position)
    }

    private fun onIndicationViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mvp_finished_without_rx_and_dagger_item_progress_bar, parent, false)
        return ProgressBarViewHolder(view)
    }

    private fun onGenericItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        when (viewType) {
            VIEW_TYPE_GALLERY -> view = LayoutInflater.from(parent.context).inflate(R.layout.mvp_finished_without_rx_and_dagger_item_character_list, parent, false)

            VIEW_TYPE_LIST -> view = LayoutInflater.from(parent.context).inflate(R.layout.mvp_finished_without_rx_and_dagger_item_character_list, parent, false)
        }
        return CharacterViewHolder(view!!)
    }

    private fun onBindGenericItemViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.name.setText(mCharacterList[position].name)

        val characterImageUrl = mCharacterList[position].imageUrl
        if (!TextUtils.isEmpty(characterImageUrl)) {
            Picasso.with(holder.listItem.context)
                    .load(characterImageUrl)
                    .centerCrop()
                    .fit()
                    .into(holder.image)
        }
    }

    fun add(item: CharacterMarvel?) {
        add(null, item)
    }

    fun add(position: Int?, item: CharacterMarvel?) {
        if (position != null) {
            mCharacterList.add(position, item!!)
            notifyItemInserted(position)
        } else {
            mCharacterList.add(item!!)
            notifyItemInserted(mCharacterList.size - 1)
        }
    }

    fun addItems(itemsList: List<CharacterMarvel>) {
        mCharacterList.addAll(itemsList)
        notifyItemRangeInserted(itemCount, mCharacterList.size - 1)
    }

    fun remove(position: Int) {
        if (mCharacterList.size < position) {
            Log.w(TAG, "The item at position: $position doesn't exist")
            return
        }
        mCharacterList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeAll() {
        mCharacterList.clear()
        notifyDataSetChanged()
    }

    fun addLoadingView(): Boolean {
        if (getItemViewType(mCharacterList.size - 1) != VIEW_TYPE_LOADING) {
            add(null)
            return true
        }
        return false
    }

    fun removeLoadingView(): Boolean {
        if (mCharacterList.size > 1) {
            val loadingViewPosition = mCharacterList.size - 1
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition)
                return true
            }
        }
        return false
    }


    /**
     * ViewHolders
     */
    inner class ProgressBarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val progressBar: ProgressBar

        init {
            progressBar = view.findViewById(R.id.progress_bar) as ProgressBar
        }
    }

    inner class CharacterViewHolder(val listItem: View) : RecyclerView.ViewHolder(listItem) {
        val name: TextView
        val image: AppCompatImageView

        init {
            name = listItem.findViewById(R.id.name) as TextView
            image = listItem.findViewById(R.id.image) as AppCompatImageView
            listItem.setOnClickListener {
                if (mListInteractionListener != null) {
                    mListInteractionListener!!.onListClick(mCharacterList[adapterPosition],
                            image, adapterPosition)
                }
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

    /**
     * Interface for handling list interactions
     */
    interface InteractionListener {
        fun onListClick(character: CharacterMarvel, sharedElementView: View, adapterPosition: Int)
    }

    fun setListInteractionListener(listInteractionListener: InteractionListener) {
        mListInteractionListener = listInteractionListener
    }

    companion object {

        val TAG = ListAdapter::class.java.simpleName

        /**
         * ViewTypes serve as a mapping point to which layout should be inflated
         */
       const val VIEW_TYPE_GALLERY = 0
       const val VIEW_TYPE_LIST = 1
       const val VIEW_TYPE_LOADING = 2
    }
}
