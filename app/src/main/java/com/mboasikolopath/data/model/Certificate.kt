package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Series::class,
        parentColumns = ["SeriesID"],
        childColumns = ["SeriesID"],
        onDelete = ForeignKey.CASCADE
    )/*, ForeignKey(
        entity = User::class,
        parentColumns = ["UserID"],
        childColumns = ["UserID"],
        onDelete = ForeignKey.CASCADE
    )*/],
    indices = [
        Index(value = ["CertificateID", "SeriesID"], unique = true)
    ]
)
data class Certificate(
    @PrimaryKey
    @SerializedName("certificateid") var CertificateID: String,

    @SerializedName("seriesid") var SeriesID: String
    /*var UserID: Int*/
)