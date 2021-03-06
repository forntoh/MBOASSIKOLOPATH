package com.mboasikolopath.data.repository

import androidx.paging.DataSource
import com.mboasikolopath.data.db.JobDao
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.internal.runOnIoThread
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class JobRepoImpl(
    private val jobDao: JobDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : JobRepo() {

    override suspend fun initData() = initJobsData()

    init {
        appDataSource.downloadedJobs.observeForever {
            it?.let { scope.launch { saveJobs(it)  }}
        }
    }

    private suspend fun saveJobs(jobs: List<Job>) {
        jobDao.insertAll(*jobs.toTypedArray())
        appStorage.setLastSaved(DataKey.JOBS, ZonedDateTime.now())
    }

    private suspend fun initJobsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.JOBS))) {
            appDataSource.jobs()
        }
    }

    override suspend fun loadAllPaged(): DataSource.Factory<Int, Job> = runOnIoThread {
        initJobsData()
        jobDao.loadAllPaged()
    }

    override suspend fun findByJobID(id: Int) = runOnIoThread {
        jobDao.findByJobID(id)
    }

    override suspend fun searchJob(query: String) = runOnIoThread {
        jobDao.searchJob("%$query%")
    }
}