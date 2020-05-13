package com.mboasikolopath.ui.main.home.explore.jobs

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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

    private val pageListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(15)
        .setPageSize(30)
        .build()

    lateinit var lifecycleOwner: LifecycleOwner

    init {
        jobRepo.scope = viewModelScope
        seriesJobRepo.scope = viewModelScope
        specialityRepo.scope = viewModelScope

        viewModelScope.launch(Dispatchers.IO) {
            val factory: DataSource.Factory<Int, Job> = jobRepo.loadAllPaged()
            val pagedListBuilder: LivePagedListBuilder<Int, Job> = LivePagedListBuilder(factory, pageListConfig)
            runOnUiThread { pagedListBuilder.build().observe(lifecycleOwner, Observer { _jobsLiveData.postValue(it) }) }
        }
    }

    private val _jobsLiveData = MutableLiveData<PagedList<Job>>()
    val jobsLiveData: LiveData<PagedList<Job>>
        get() = _jobsLiveData

    suspend fun searchJobs(query: String) = runOnIoThread {
        val factory: DataSource.Factory<Int, Job> = jobRepo.searchJob(query)
        val pagedListBuilder: LivePagedListBuilder<Int, Job>  = LivePagedListBuilder(factory, pageListConfig)
        runOnUiThread { pagedListBuilder.build().observe(lifecycleOwner, Observer { _jobsLiveData.postValue(it) }) }
    }

    suspend fun getSpecialityOfJob(jobId: Int) = runOnIoThread { seriesJobRepo.findSeriesByJobID(jobId).joinToString {
        runBlocking { specialityRepo.findBySpecialityID(it.SpecialityID).Description }
    }}

    suspend fun getSeriesOfJob(jobId: Int) = runOnIoThread {
        seriesJobRepo.findSeriesByJobID(jobId)
    }
}
