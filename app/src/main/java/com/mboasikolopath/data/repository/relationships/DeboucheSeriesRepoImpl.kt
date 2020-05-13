package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.db.relationships.DeboucheSeriesDao
import com.mboasikolopath.data.model.relationships.DeboucheSeries
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class DeboucheSeriesRepoImpl(
    private val deboucheSeriesDao: DeboucheSeriesDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : DeboucheSeriesRepo() {

    override suspend fun initData() = initDebouchesSeriesData()

    init {
        appDataSource.downloadedDebouchesSeries.observeForever {
            scope.launch { saveDebouchesSeries(it) }
        }
    }

    private suspend fun saveDebouchesSeries(deboucheSeries: List<DeboucheSeries>) {
        deboucheSeriesDao.insertAll(*deboucheSeries.toTypedArray())
        appStorage.setLastSaved(DataKey.DEBOUCHES_SERIES, ZonedDateTime.now())
    }

    private suspend fun initDebouchesSeriesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.DEBOUCHES_SERIES))) {
            appDataSource.certificateDebouches()
        }
    }

    override suspend fun findDeboucheAndSeriesPairs() = withContext(Dispatchers.IO) {
        initDebouchesSeriesData()
        deboucheSeriesDao.findDeboucheAndSeriesPairs()
    }

    override suspend fun findDebouchesBySeriesID(id: String, cycle: Int) = withContext(Dispatchers.IO) {
        deboucheSeriesDao.findDebouchesBySeriesID(id, cycle)
    }
}