package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Region(
    @PrimaryKey
    @SerializedName("regionid") var RegionID: Int,
    @SerializedName("name") var Name: String
)