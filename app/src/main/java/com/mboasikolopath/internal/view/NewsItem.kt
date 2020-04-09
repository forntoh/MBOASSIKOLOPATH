package com.mboasikolopath.internal.view

import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.mboasikolopath.R
import com.mboasikolopath.utilities.screenWidth
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_news.view.*

data class NewsItem(
    var _id: Int = 0,
    var title: String = "",
    var thumbnail: String = "",
    var description: String = ""
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        (viewHolder.itemView.news_thumbnail.layoutParams as LinearLayout.LayoutParams).apply {
            height = (screenWidth * (2f / 7f)).toInt()
            width = (screenWidth * (2f / 4f)).toInt()
        }
        viewHolder.itemView.news_title.layoutParams.width = (screenWidth * (2f / 4f)).toInt()
        viewHolder.itemView.news_title.text = title
        Glide.with(viewHolder.itemView.context).load(thumbnail).into(viewHolder.itemView.news_thumbnail)
    }

    override fun getLayout() = R.layout.item_news
}