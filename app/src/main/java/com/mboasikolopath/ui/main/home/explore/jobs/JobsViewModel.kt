package com.mboasikolopath.ui.main.home.explore.jobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.repository.JobRepo
import com.mboasikolopath.data.repository.SpecialityRepo
import com.mboasikolopath.data.repository.relationships.SeriesJobRepo
import com.mboasikolopath.internal.runOnIoThread
import com.mboasikolopath.internal.runOnUiThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class JobsViewModel(
    private val jobRepo: JobRepo,
    private val seriesJobRepo: SeriesJobRepo,
    private val specialityRepo: SpecialityRepo
) : ViewModel() {

    private val pageSize = 15

    private val pageListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders (false)
        .setPageSize           (pageSize)
        .setPrefetchDistance   (pageSize * 2)
        .setInitialLoadSizeHint(pageSize * 3)
        .setMaxSize            (pageSize * 9)
        .build()


    init {
        jobRepo.scope = viewModelScope
        seriesJobRepo.scope = viewModelScope
        specialityRepo.scope = viewModelScope

        viewModelScope.launch(Dispatchers.IO) {
            val factory: DataSource.Factory<Int, Job> = jobRepo.loadAllPaged()
            runOnUiThread {
                factory.toLiveData(pageListConfig).observeForever { _jobsLiveData.postValue(it) }
            }
        }
    }

    private val _jobsLiveData = MutableLiveData<PagedList<Job>>()
    val jobsLiveData: LiveData<PagedList<Job>>
        get() = _jobsLiveData

    suspend fun searchJobs(query: String) = runOnIoThread {
        val factory: DataSource.Factory<Int, Job> = jobRepo.searchJob(query)
        runOnUiThread {
            factory.toLiveData(pageListConfig).observeForever { _jobsLiveData.postValue(it) }
        }
    }

    suspend fun getSpecialityOfJob(jobId: Int) = runOnIoThread { seriesJobRepo.findSeriesByJobID(jobId).joinToString {
        runBlocking { specialityRepo.findBySpecialityID(it.SpecialityID).Description }
    }}

    suspend fun getSeriesOfJob(jobId: Int) = runOnIoThread {
        seriesJobRepo.findSeriesByJobID(jobId)
    }
}
