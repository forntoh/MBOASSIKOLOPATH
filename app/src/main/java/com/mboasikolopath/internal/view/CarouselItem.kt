package com.mboasikolopath.internal.view

import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.mboasikolopath.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

data class CarouselItem(var thumbnailUrl: String) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context).load(thumbnailUrl).placeholder(R.drawable.welcome_image).into(viewHolder.itemView as RoundedImageView)
    }

    override fun getLayout() = R.layout.item_carousel
}