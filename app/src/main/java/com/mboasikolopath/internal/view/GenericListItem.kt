package com.mboasikolopath.internal.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.chip.Chip
import com.mboasikolopath.R
import com.mboasikolopath.ui.main.home.explore.jobs.JobsFragment
import com.mboasikolopath.ui.main.home.explore.jobs.JobsFragmentDirections
import com.mboasikolopath.ui.main.home.explore.sector.SectorFragment
import com.mboasikolopath.ui.main.home.explore.sector.SectorFragmentDirections
import com.mboasikolopath.utilities.setupGroup
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_expansion.view.*
import kotlinx.android.synthetic.main.item_list_generic.view.*
import kotlinx.android.synthetic.main.item_list_generic.view.item_expansion


data class GenericListItem(
    var _id: String = "",
    var title: String? = "",
    var subTitle: String? = "",
    var longDescription: String? = "",
    var firstChipGroup: List<String> = emptyList(),
    var secondChipGroup: List<String> = emptyList()
) : Item() {

    var toExpand: Boolean = false

    private lateinit var context: Context

    companion object {
        var icon: Drawable? = null
        var fragment: Fragment? = null
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        context = viewHolder.itemView.context

        if (icon == null) icon = ContextCompat.getDrawable(context, R.drawable.ic_ellipse)
        if (subTitle.isNullOrBlank()) viewHolder.itemView.item_subtitle.visibility = View.GONE

        viewHolder.itemView.item_title.text = title
        viewHolder.itemView.item_subtitle.text = subTitle

        viewHolder.itemView.item_icon.setImageDrawable(icon)

        viewHolder.itemView.item_list_1_chips.setupGroup(
            firstChipGroup,
            viewHolder.itemView.item_list_1_title,
            chipOnClickListener
        )
        viewHolder.itemView.item_list_2_chips.setupGroup(
            secondChipGroup,
            viewHolder.itemView.item_list_2_title,
            chipOnClickListener
        )

        if (toExpand) {
            viewHolder.itemView.item_expansion.visibility = View.VISIBLE
            viewHolder.itemView.setBackgroundColor(
                ContextCompat.getColor(context, R.color.bgLightGrey)
            )
        } else {
            viewHolder.itemView.item_expansion.visibility = View.GONE
            viewHolder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    private val chipOnClickListener = View.OnClickListener {
        var direction: NavDirections? = null

        val cycle = if ((it.parent as View).id == R.id.item_list_1_chips) 1 else 2

        if (fragment is JobsFragment)
            direction = JobsFragmentDirections.actionJobsFragmentToSeriesFragment((it as Chip).text.toString(), cycle)
        else if (fragment is SectorFragment)
            direction = SectorFragmentDirections.actionSectorFragmentToSeriesFragment((it as Chip).text.toString(), cycle)

        direction?.let { dir -> NavHostFragment.findNavController(fragment!!).navigate(dir) }
    }

    override fun getLayout() = R.layout.item_list_generic
}