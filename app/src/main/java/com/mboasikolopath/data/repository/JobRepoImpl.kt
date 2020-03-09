package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.JobDao
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private suspend fun saveJobs(jobs: List<Job>) =
        jobDao.insertAll(*jobs.toTypedArray())

    private suspend fun initJobsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.JOBS))) {
            appDataSource.jobs()
            appStorage.setLastSaved(DataKey.JOBS, ZonedDateTime.now())
        }
    }

    override suspend fun loadAll() = withContext(Dispatchers.IO) {
        initJobsData()
        return@withContext jobDao.loadAll()
    }

    override suspend fun findByJobID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext jobDao.findByJobID(id)
    }

    override suspend fun searchJob(query: String) = withContext(Dispatchers.IO) {
        return@withContext jobDao.searchJob("%$query%")
    }
}