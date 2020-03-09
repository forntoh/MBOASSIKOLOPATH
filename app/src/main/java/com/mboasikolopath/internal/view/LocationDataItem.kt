package com.mboasikolopath.internal.view

import com.mboasikolopath.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_location_data.view.*

data class LocationDataItem(
    var key: String,
    var value: String
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_key.text = key
        viewHolder.itemView.btn_value.text = value
    }

    override fun getLayout() = R.layout.item_location_data
}