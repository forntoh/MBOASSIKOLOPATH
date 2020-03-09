package com.mboasikolopath.data.model.relationships

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.pairs.SeriesJobPair

@Entity(
    primaryKeys = ["SeriesID", "JobID"], foreignKeys = [ForeignKey(
        entity = Series::class,
        parentColumns = ["SeriesID"],
        childColumns = ["SeriesID"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Job::class,
        parentColumns = ["JobID"],
        childColumns = ["JobID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["SeriesID", "JobID"], unique = true)]

)
data class SeriesJob(
    @SerializedName("seriesid") var SeriesID: String,
    @SerializedName("jobid") var JobID: Int
) {
    companion object {
        fun groupJobs(SeriesJobList: List<SeriesJobPair>): MutableList<SeriesAndItsJobs> {
            return mutableListOf<SeriesAndItsJobs>().also { items ->
                SeriesJobList
                    .groupBy(keySelector = { it.series }, valueTransform = { it.job })
                    .forEach { items.add(SeriesAndItsJobs(it.key, it.value)) }
            }
        }
        fun groupSeries(SeriesJobList: List<SeriesJobPair>): MutableList<JobsAndItsSeries> {
            return mutableListOf<JobsAndItsSeries>().also { items ->
                SeriesJobList
                    .groupBy(keySelector = { it.job }, valueTransform = { it.series })
                    .forEach { items.add(JobsAndItsSeries(it.key, it.value)) }
            }
        }
    }
}

data class SeriesAndItsJobs(
    val Series: Series?,
    val Jobs: List<Job>
)

data class JobsAndItsSeries(
    val job: Job?,
    val series: List<Series>
)