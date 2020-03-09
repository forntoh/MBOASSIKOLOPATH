package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.db.relationships.SeriesJobDao
import com.mboasikolopath.data.model.relationships.JobsAndItsSeries
import com.mboasikolopath.data.model.relationships.SeriesAndItsJobs
import com.mboasikolopath.data.model.relationships.SeriesJob
import com.mboasikolopath.data.model.relationships.pairs.SeriesJobPair
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SeriesJobRepoImpl(
    private val seriesJobDao: SeriesJobDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SeriesJobRepo() {

    override suspend fun initData() = initSeriesJobData()

    init {
        appDataSource.downloadedSeriesJobs.observeForever {
            scope.launch { saveSeriesJobs(it) }
        }
    }

    private suspend fun saveSeriesJobs(seriesJobs: List<SeriesJob>) =
        seriesJobDao.insertAll(*seriesJobs.toTypedArray())

    private suspend fun initSeriesJobData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SERIES_JOBS))) {
            appDataSource.seriesJobs()
            appStorage.setLastSaved(DataKey.SERIES_JOBS, ZonedDateTime.now())
        }
    }

    private suspend fun loadData(): List<SeriesJobPair> {
        initSeriesJobData()
        val data = seriesJobDao.findSeriesAndJobPairs()
        if (data.isNullOrEmpty()) {
            appStorage.clearLastSaved(DataKey.SERIES_JOBS)
            initSeriesJobData()
        }
        return data
    }

    override suspend fun getSeriesAndItsJobs(): List<SeriesAndItsJobs> = withContext(Dispatchers.IO) {
        val data = loadData()
        return@withContext if (!data.isNullOrEmpty()) SeriesJob.groupJobs(data)
        else emptyList<SeriesAndItsJobs>()
    }

    override suspend fun getJobAndItsSeries(): List<JobsAndItsSeries> = withContext(Dispatchers.IO) {
        val data = loadData()
        return@withContext if (!data.isNullOrEmpty()) SeriesJob.groupSeries(data)
        else emptyList<JobsAndItsSeries>()
    }

    override suspend fun findJobsBySeriesID(id: String) = withContext(Dispatchers.IO) {
        initSeriesJobData()
        val data = seriesJobDao.findJobsBySeriesID(id)
        if (data.isNullOrEmpty()) {
            appStorage.clearLastSaved(DataKey.SERIES_JOBS)
            initSeriesJobData()
        }
        return@withContext data
    }

    override suspend fun findSeriesByJobID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext seriesJobDao.findSeriesByJobID(id)
    }
}