package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Speciality::class,
        parentColumns = ["SpecialityID"],
        childColumns = ["SpecialityID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["SeriesID", "Cycle"], unique = true), Index(value = ["SpecialityID"])]
)
data class Series(
    @PrimaryKey
    @SerializedName("seriesid") var SeriesID: String,
    @SerializedName("name") var Name: String,
    @SerializedName("cycle") var Cycle: Int,

    @SerializedName("specialityid") var SpecialityID: Int
)