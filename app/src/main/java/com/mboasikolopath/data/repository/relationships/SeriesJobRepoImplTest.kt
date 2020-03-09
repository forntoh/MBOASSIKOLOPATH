package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.JobsAndItsSeries
import com.mboasikolopath.data.model.relationships.SeriesAndItsJobs
import com.mboasikolopath.data.repository.JobRepo
import com.mboasikolopath.data.repository.SeriesRepo

class SeriesJobRepoImplTest(val jobRepo: JobRepo, val seriesRepo: SeriesRepo) : SeriesJobRepo() {

    override suspend fun initData() = Unit

    override suspend fun findSeriesByJobID(id: Int): List<Series> {
        return getJobAndItsSeries().find { it.job!!.JobID == id }!!.series
    }

    override suspend fun getJobAndItsSeries(): List<JobsAndItsSeries> {
        val list = mutableListOf<JobsAndItsSeries>()

        for (i in 0..10) {

            val subList = mutableListOf<Series>()
            for (j in 0..15) {
                subList.add(seriesRepo.loadAll()[j])
            }

            list.add(
                JobsAndItsSeries(
                    jobRepo.findByJobID(i),
                    subList
                )
            )
        }
        return list
    }

    override suspend fun getSeriesAndItsJobs(): List<SeriesAndItsJobs> {
        val list = mutableListOf<SeriesAndItsJobs>()

        for (i in 0..16) {

            val subList = mutableListOf<Job>()
            for (j in 0..11) {
                subList.add(jobRepo.loadAll()[j])
            }

            list.add(
                SeriesAndItsJobs(
                    seriesRepo.loadAll()[i],
                    subList
                )
            )
        }
        return list
    }

    override suspend fun findJobsBySeriesID(id: String) = jobRepo.loadAll()
}