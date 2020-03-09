package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.SeriesDao
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SeriesRepoImpl(
    private val seriesDao: SeriesDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SeriesRepo() {

    override suspend fun initData() = initSeriesData()

    init {
        appDataSource.downloadedSeries.observeForever {
            it?.let { scope.launch { saveSeries(it) } }
        }
    }

    private suspend fun saveSeries(series: List<Series>) =
        seriesDao.insertAll(*series.toTypedArray())

    private suspend fun initSeriesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SERIES))) {
            appDataSource.series()
            appStorage.setLastSaved(DataKey.SERIES, ZonedDateTime.now())
        }
    }

    override suspend fun loadAll() = withContext(Dispatchers.IO) {
        initSeriesData()
        return@withContext seriesDao.loadAll()
    }

    override suspend fun findBySeriesID(id: String) = withContext(Dispatchers.IO) {
        return@withContext seriesDao.findBySeriesID(id)
    }

    override suspend fun findSeriesOfSpeciality(id: Int) = withContext(Dispatchers.IO) {
        return@withContext seriesDao.findSeriesOfSpeciality(id)
    }
}