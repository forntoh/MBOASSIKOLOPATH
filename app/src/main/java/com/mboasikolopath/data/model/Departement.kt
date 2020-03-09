package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Region::class,
        parentColumns = ["RegionID"],
        childColumns = ["RegionID"],
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )],
    indices = [Index(value = ["RegionID"], unique = false)]
)
data class Departement(
    @PrimaryKey
    @SerializedName("departementid") var DepartementID: Int,
    @SerializedName("name") var Name: String,

    @SerializedName("regionid") var RegionID: Int
)