package com.mboasikolopath.data.model.relationships

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.SubjectTaught
import com.mboasikolopath.data.model.relationships.pairs.SeriesSubjectTaughtPair

@Entity(
    primaryKeys = ["SeriesID", "SubjectTaughtID"], foreignKeys = [ForeignKey(
        entity = Series::class,
        parentColumns = ["SeriesID"],
        childColumns = ["SeriesID"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = SubjectTaught::class,
        parentColumns = ["SubjectTaughtID"],
        childColumns = ["SubjectTaughtID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["SeriesID", "SubjectTaughtID"], unique = true)]
)
data class SeriesSubjectTaught(
    @SerializedName("seriesid") var SeriesID: String,
    @SerializedName("subjecttaughtid") var SubjectTaughtID: Int
) {
    companion object {
        fun groupSubjectsTaught(SeriesSubjectTaughtList: List<SeriesSubjectTaughtPair>): MutableList<SeriesAndItsSubjectsTaught> {
            return mutableListOf<SeriesAndItsSubjectsTaught>().also { items ->
                SeriesSubjectTaughtList
                    .groupBy(keySelector = { it.Series }, valueTransform = { it.SubjectTaught })
                    .forEach { items.add(SeriesAndItsSubjectsTaught(it.key, it.value)) }
            }
        }
    }
}

data class SeriesAndItsSubjectsTaught(
    val Series: Series?,
    val SubjectsTaught: List<SubjectTaught>
)