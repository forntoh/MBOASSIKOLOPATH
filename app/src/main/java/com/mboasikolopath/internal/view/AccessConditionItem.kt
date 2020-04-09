package com.mboasikolopath.internal.view

import com.mboasikolopath.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_access_condition.view.*

data class AccessConditionItem(
    var text: String,
    var validated: Boolean
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_condition.text = text
    }

    override fun getLayout() = R.layout.item_access_condition
}