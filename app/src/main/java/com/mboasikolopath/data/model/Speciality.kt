package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Speciality(
    @PrimaryKey
    @SerializedName("specialityid") var SpecialityID: Int,
    @SerializedName("description") var Description: String
)