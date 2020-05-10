package com.mboasikolopath.data.model.relationships

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.pairs.DeboucheSeriesPair

@Entity(
    primaryKeys = ["DeboucheID", "SeriesID"],
    foreignKeys = [ForeignKey(
        entity = Debouche::class,
        parentColumns = ["DeboucheID"],
        childColumns = ["DeboucheID"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Series::class,
        parentColumns = ["SeriesID"],
        childColumns = ["SeriesID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["DeboucheID", "SeriesID"], unique = true)]
)
data class DeboucheSeries(
    @ColumnInfo var DeboucheID: String,
    @ColumnInfo var SeriesID: Int
) {
    companion object {
        fun groupSeries(DeboucheSeriesList: List<DeboucheSeriesPair>): MutableList<DeboucheAndItsSeries> {
            return mutableListOf<DeboucheAndItsSeries>().also { items ->
                DeboucheSeriesList
                    .groupBy(keySelector = { it.Debouche }, valueTransform = { it.Series })
                    .forEach { items.add(DeboucheAndItsSeries(it.key, it.value)) }
            }
        }

        fun groupDebouches(SeriesDeboucheList: List<DeboucheSeriesPair>): MutableList<SeriesAndItsDebouches> {
            return mutableListOf<SeriesAndItsDebouches>().also { items ->
                SeriesDeboucheList
                    .groupBy(keySelector = { it.Series }, valueTransform = { it.Debouche })
                    .forEach { items.add(SeriesAndItsDebouches(it.key, it.value)) }
            }
        }
    }
}

data class DeboucheAndItsSeries(
    val Debouche: Debouche?,
    val Series: List<Series>
)

data class SeriesAndItsDebouches(
    val Series: Series?,
    val Debouches: List<Debouche>
)