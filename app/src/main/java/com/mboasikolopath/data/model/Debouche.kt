package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Debouche(
    @PrimaryKey
    @SerializedName("deboucheid") var DeboucheID: Int,
    @SerializedName("name") var Name: String
)