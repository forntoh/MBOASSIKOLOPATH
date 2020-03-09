package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.JobsAndItsSeries
import com.mboasikolopath.data.model.relationships.SeriesAndItsJobs
import com.mboasikolopath.data.repository.BaseRepository

abstract class SeriesJobRepo: BaseRepository() {

    abstract suspend fun getSeriesAndItsJobs(): List<SeriesAndItsJobs>

    abstract suspend fun getJobAndItsSeries(): List<JobsAndItsSeries>

    abstract suspend fun findJobsBySeriesID(id: String): List<Job>

    abstract suspend fun findSeriesByJobID(id: Int): List<Series>
}