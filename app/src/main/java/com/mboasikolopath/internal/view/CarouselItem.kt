package com.mboasikolopath.internal.view

import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.mboasikolopath.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

data class CarouselItem(var thumbnailUrl: String) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context).load(thumbnailUrl).into(viewHolder.itemView as RoundedImageView)
    }

    override fun getLayout() = R.layout.item_carousel
}