package com.mboasikolopath.internal

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mboasikolopath.R
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.School
import kotlinx.android.synthetic.main.item_list_generic.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenericItemPagedListAdapter<T : Any>(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val subtitleListener: SubtitleListener
) : PagedListAdapter<T, GenericItemPagedListAdapter.GenericItemViewHolder>(object :
    DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = when(oldItem) {
        is School -> oldItem.SchoolID == (newItem as School).SchoolID
        is Job -> oldItem.JobID == (newItem as Job).JobID
        else -> true
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = newItem == oldItem

}) {

    companion object {
        var icon: Drawable? = null
    }

    interface SubtitleListener {
        suspend fun genSubtitle(id: Int): String
    }

    interface OnItemClickListener {
        fun <T> onItemClick(item: T)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericItemViewHolder =
        GenericItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_generic, parent, false),
            coroutineScope,
            subtitleListener
        )

    override fun onBindViewHolder(holder: GenericItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null) holder.clear()
        else holder.bind(item)
        holder.parent?.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    class GenericItemViewHolder(
        private val view: View,
        private val coroutineScope: CoroutineScope,
        private val subtitleListener: SubtitleListener
    ) : RecyclerView.ViewHolder(view) {

        private var titleText = ""
        private var subtitleText = ""

        var parent: ViewGroup? = null

        fun <T> bind(item: T)  = coroutineScope.launch {
            parseData(item)
            view.item_title.text = titleText
            view.item_subtitle.text = subtitleText
            view.item_icon.setImageDrawable(icon)
            if (subtitleText.isBlank()) view.item_subtitle.visibility = View.GONE
            view.item_expansion.visibility = View.GONE
            parent = view.item_title.parent as ViewGroup?
        }

        private suspend fun <T> parseData(item: T) {
            when(item) {
                is School -> {
                    titleText = item.Name
                    subtitleText = withContext(Dispatchers.IO) {
                        subtitleListener.genSubtitle(item.LocaliteID)
                    }
                }
                is Job -> {
                    titleText = item.Name
                    subtitleText = withContext(Dispatchers.IO) {
                        subtitleListener.genSubtitle(item.JobID)
                    }
                }
            }
        }

        fun clear() {
            view.item_title.text = null
            view.item_subtitle.text = null
        }

    }
}