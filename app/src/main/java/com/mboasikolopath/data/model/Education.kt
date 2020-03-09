package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Education(
    @PrimaryKey
    @SerializedName("educationid") var EducationID: Int,
    @SerializedName("description") var Description: String
)