package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Departement::class,
        parentColumns = ["DepartementID"],
        childColumns = ["DepartementID"],
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )],
    indices = [Index(value = ["DepartementID"], unique = false)]
)
data class Arrondissement(
    @PrimaryKey
    @SerializedName("arrondissementid") var ArrondissementID: Int,
    @SerializedName("name") var Name: String,

    @SerializedName("departementid") var DepartementID: Int
)