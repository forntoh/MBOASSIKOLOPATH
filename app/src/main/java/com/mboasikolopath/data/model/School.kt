package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Localite::class,
        parentColumns = ["LocaliteID"],
        childColumns = ["LocaliteID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["LocaliteID"], unique = false)]
)
data class School(
    @PrimaryKey
    @SerializedName("schoolid") var SchoolID: Int,
    @SerializedName("name") var Name: String,

    @SerializedName("localiteid")  var LocaliteID: Int
)