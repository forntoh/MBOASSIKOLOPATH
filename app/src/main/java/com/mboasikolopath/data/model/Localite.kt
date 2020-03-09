package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Arrondissement::class,
        parentColumns = ["ArrondissementID"],
        childColumns = ["ArrondissementID"],
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )],
    indices = [Index(value = ["ArrondissementID"], unique = false)]
)
data class Localite(
    @PrimaryKey
    @SerializedName("localiteid") var LocaliteID: Int,
    @SerializedName("name") var Name: String,

    @SerializedName("arrondissementid") var ArrondissementID: Int
)