package com.mboasikolopath.internal.view

import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.mboasikolopath.R
import com.mboasikolopath.utilities.screenWidth
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_news.view.*

data class NewsItem(
    var title: String = "",
    var thumbnail: String = "https://media.wsls.com/photo/2017/04/24/Whats%20News%20Today_1493062809311_9576980_ver1.0_1280_720.png",
    var description: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
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