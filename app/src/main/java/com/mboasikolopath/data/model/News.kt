package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class News(
    @PrimaryKey
    @SerializedName("newsid") var NewsID: Int,
    @SerializedName("title") var Title: String,
    @SerializedName("body") var Body: String,
    @SerializedName("thumbnail") var Thumbnail: String
)