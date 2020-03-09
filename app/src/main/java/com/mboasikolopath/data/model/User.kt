package com.mboasikolopath.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Localite::class,
        parentColumns = ["LocaliteID"],
        childColumns = ["LocaliteID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["LocaliteID"])]
)
data class User(
    @PrimaryKey
    @SerializedName("userid") var UserID: Int,
    @SerializedName("username") var Username: String,
    @SerializedName("name") var Name: String,
    @SerializedName("password") var Password: String? = null,
    @SerializedName("dob") var Dob: String? = null,
    @SerializedName("gender") var Gender: String = "M",
    @SerializedName("api_token") var Token: String? = null,

    @SerializedName("localiteid") var LocaliteID: Int = -1,

    var error: String? = null
) {
    constructor() : this(1, "", "")

    constructor(error: String) : this(1, "", "", null, null, "M", null, 1, error)

    fun toMap(): Map<String, String> = ObjectMapper().readValue(
        Gson().toJson(this),
        object : TypeReference<Map<String, String>>() {}
    )
}