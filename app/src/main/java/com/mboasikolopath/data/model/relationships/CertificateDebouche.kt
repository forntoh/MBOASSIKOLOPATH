package com.mboasikolopath.data.model.relationships

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.mboasikolopath.data.model.Certificate
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.relationships.pairs.CertificateDebouchePair

@Entity(
    primaryKeys = ["CertificateID", "DeboucheID"], foreignKeys = [ForeignKey(
        entity = Certificate::class,
        parentColumns = ["CertificateID"],
        childColumns = ["CertificateID"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Debouche::class,
        parentColumns = ["DeboucheID"],
        childColumns = ["DeboucheID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["CertificateID", "DeboucheID"], unique = true)]
)
data class CertificateDebouche(
    @SerializedName("certificateid") var CertificateID: String,
    @SerializedName("deboucheid") var DeboucheID: Int
) {
    companion object {
        fun groupDebouches(CertificateDeboucheList: List<CertificateDebouchePair>): MutableList<CertificateAndItsDebouches> {
            return mutableListOf<CertificateAndItsDebouches>().also { items ->
                CertificateDeboucheList
                    .groupBy(keySelector = { it.Certificate }, valueTransform = { it.Debouche })
                    .forEach { items.add(CertificateAndItsDebouches(it.key, it.value)) }
            }
        }
    }
}

data class CertificateAndItsDebouches(
    val Certificate: Certificate?,
    val Debouches: List<Debouche>
)