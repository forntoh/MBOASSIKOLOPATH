package com.mboasikolopath.internal.view

import com.mboasikolopath.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_access_condition.view.*

data class AccessConditionItem(
    var text: String,
    var validated: Boolean
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_condition.text = text
    }

    override fun getLayout() = R.layout.item_access_condition
}