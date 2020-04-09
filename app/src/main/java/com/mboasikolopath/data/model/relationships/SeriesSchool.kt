package com.mboasikolopath.data.model.relationships

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.pairs.SeriesSchoolPair

@Entity(
    primaryKeys = ["SeriesID", "SchoolID"], foreignKeys = [ForeignKey(
        entity = Series::class,
        parentColumns = ["SeriesID"],
        childColumns = ["SeriesID"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = School::class,
        parentColumns = ["SchoolID"],
        childColumns = ["SchoolID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["SeriesID", "SchoolID"], unique = true)]
)
data class SeriesSchool(
    @SerializedName("seriesid") var SeriesID: String,
    @SerializedName("schoolid") var SchoolID: Int
) {
    companion object {
        fun groupSchools(SeriesSchoolList: List<SeriesSchoolPair>): MutableList<SeriesAndItsSchools> {
            return mutableListOf<SeriesAndItsSchools>().also { items ->
                SeriesSchoolList
                    .groupBy(keySelector = { it.Series }, valueTransform = { it.School })
                    .forEach { items.add(SeriesAndItsSchools(it.key, it.value)) }
            }
        }

        fun groupSeries(SchoolSeriesList: List<SeriesSchoolPair>): MutableList<SchoolAndItsSeries> {
            return mutableListOf<SchoolAndItsSeries>().also { items ->
                SchoolSeriesList
                    .groupBy(keySelector = { it.School }, valueTransform = { it.Series })
                    .forEach { items.add(SchoolAndItsSeries(it.key, it.value)) }
            }
        }
    }
}

data class SeriesAndItsSchools(
    val Series: Series?,
    val Schools: List<School>
)

data class SchoolAndItsSeries(
    val School: School?,
    val Series: List<Series>
)