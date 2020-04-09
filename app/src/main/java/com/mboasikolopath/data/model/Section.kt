package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Education::class,
        parentColumns = ["EducationID"],
        childColumns = ["EducationID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["EducationID"], unique = false)]
)
data class Section(
    @PrimaryKey
    @SerializedName("sectionid") var SectionID: Int,
    @SerializedName("description") var Description: String,
    @SerializedName("longdescription") var LongDescription: String? = "",

    @SerializedName("educationid") var EducationID: Int
)