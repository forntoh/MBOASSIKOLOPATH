package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Job(
    @PrimaryKey
    @SerializedName("jobid") var JobID: Int,
    @SerializedName("name") var Name: String
)