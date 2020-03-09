package com.mboasikolopath.ui.main.home.explore.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mboasikolopath.data.repository.JobRepo
import com.mboasikolopath.data.repository.SpecialityRepo
import com.mboasikolopath.data.repository.relationships.SeriesJobRepo
import com.mboasikolopath.internal.lazyDeferred
import com.mboasikolopath.internal.view.GenericListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class JobsViewModel(
    private val jobRepo: JobRepo,
    private val seriesJobRepo: SeriesJobRepo,
    private val specialityRepo: SpecialityRepo
) : ViewModel() {

    init {
        jobRepo.scope = viewModelScope
        seriesJobRepo.scope = viewModelScope
        specialityRepo.scope = viewModelScope
    }

    val jobs by lazyDeferred {
        seriesJobRepo.getJobAndItsSeries().map { jobsAndItsSeries ->
            GenericListItem(
                jobsAndItsSeries.job!!.JobID.toString(),
                jobsAndItsSeries.job.Name,
                jobsAndItsSeries.series.joinToString {
                    runBlocking(this.coroutineContext) {
                        specialityRepo.findBySpecialityID(
                            it.SpecialityID
                        ).Description
                    }
                },"",
                jobsAndItsSeries.series.filter { it.Cycle == 1 }.map { it.SeriesID },
                jobsAndItsSeries.series.filter { it.Cycle == 2 }.map { it.SeriesID }
            )
        }
    }

    suspend fun searchJobs(query: String): List<GenericListItem> = withContext(Dispatchers.IO) {
        val list = mutableListOf<GenericListItem>()
        jobRepo.searchJob(query).forEach { job ->
            val series = seriesJobRepo.findSeriesByJobID(job.JobID)
            list.add(
                GenericListItem(
                    job.JobID.toString(),
                    job.Name,
                    series.joinToString {
                        runBlocking(this.coroutineContext) {
                            specialityRepo.findBySpecialityID(
                                it.SpecialityID
                            ).Description
                        }
                    },"",
                    series.filter { it.Cycle == 1 }.map { it.SeriesID },
                    series.filter { it.Cycle == 2 }.map { it.SeriesID }
                )
            )
        }
        return@withContext list
    }
}
