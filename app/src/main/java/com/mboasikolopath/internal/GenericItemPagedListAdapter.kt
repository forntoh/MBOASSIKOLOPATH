package com.mboasikolopath.internal

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mboasikolopath.R
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.utilities.setupGroup
import kotlinx.android.synthetic.main.item_expansion.view.*
import kotlinx.android.synthetic.main.item_list_generic.view.*
import kotlinx.android.synthetic.main.item_list_generic.view.item_expansion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GenericItemPagedListAdapter<T : Any>(
    private val context: Context,
    private val coroutineScope: CoroutineScope
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

    interface ChipItemsListener {
        suspend fun getSeriesOfJob(id: Int): List<Series>
    }

    var subtitleListener: SubtitleListener? = null
    var chipItemsListener: ChipItemsListener? = null
    var onItemClickListener: OnItemClickListener? = null
    var chipOnClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericItemViewHolder =
        GenericItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_generic, parent, false),
            coroutineScope,
            subtitleListener,
            chipItemsListener,
            chipOnClickListener
        )

    override fun onBindViewHolder(holder: GenericItemViewHolder, position: Int) {
        holder.toExpand = false
        val item = getItem(position)
        if (item == null) holder.clear()
        else holder.bind(item)

        holder.view.setOnClickListener {
            when (item) {
                is Job -> {
                    holder.toExpand = !holder.toExpand
                    holder.bind(item)
                }
                is School -> onItemClickListener?.onItemClick(item)
            }
        }
    }

    class GenericItemViewHolder(
        val view: View,
        private val coroutineScope: CoroutineScope,
        private val subtitleListener: SubtitleListener?,
        private var chipItemsListener: ChipItemsListener?,
        private var chipOnClickListener: View.OnClickListener?
    ) : RecyclerView.ViewHolder(view) {

        var toExpand = false
        private var titleText = ""
        private var subtitleText = ""
        private var firstChipGroup: List<String> = emptyList()
        private var secondChipGroup: List<String> = emptyList()

        fun <T> bind(item: T)  = coroutineScope.launch {
            parseData(item)

            if (subtitleText.isBlank()) view.item_subtitle.visibility = View.GONE

            view.item_title.text = titleText
            view.item_subtitle.text = subtitleText
            view.item_icon.setImageDrawable(icon)

            view.item_list_1_chips.setupGroup(
                firstChipGroup,
                view.item_list_1_title,
                chipOnClickListener
            )
            view.item_list_2_chips.setupGroup(
                secondChipGroup,
                view.item_list_2_title,
                chipOnClickListener
            )

            if (toExpand) {
                view.item_expansion.visibility = View.VISIBLE
                view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.bgLightGrey))
            } else {
                view.item_expansion.visibility = View.GONE
                view.setBackgroundColor(Color.WHITE)
            }
        }

        private suspend fun <T> parseData(item: T) {
            when(item) {
                is School -> {
                    titleText = item.Name
                    subtitleText = runOnIoThread {
                        subtitleListener?.genSubtitle(item.LocaliteID) ?: "N/A"
                    }
                }
                is Job -> {
                    titleText = item.Name
                    subtitleText = runOnIoThread {
                        subtitleListener?.genSubtitle(item.JobID) ?: "N/A"
                    }
                    val series = runOnIoThread { chipItemsListener?.getSeriesOfJob(item.JobID) } ?: return
                    firstChipGroup = series.filter { it.Cycle == 1 }.map { it.SeriesID }
                    secondChipGroup = series.filter { it.Cycle == 2 }.map { it.SeriesID }
                }
            }
        }

        fun clear() {
            view.item_title.text = "N/A"
            view.item_subtitle.text = null
        }

    }
}