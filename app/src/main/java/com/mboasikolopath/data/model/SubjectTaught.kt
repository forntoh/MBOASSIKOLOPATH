package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SubjectTaught(
    @PrimaryKey
    @SerializedName("subjecttaughtid") var SubjectTaughtID: Int,
    @SerializedName("name") var Name: String
)