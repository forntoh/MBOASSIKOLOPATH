package com.mboasikolopath.internal.view

import com.mboasikolopath.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_location_data.view.*

data class LocationDataItem(
    var key: String,
    var value: String? = "N/A"
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_key.text = key
        viewHolder.itemView.btn_value.text = value
    }

    override fun getLayout() = R.layout.item_location_data
}