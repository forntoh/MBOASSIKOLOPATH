package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.db.relationships.SeriesSchoolDao
import com.mboasikolopath.data.model.relationships.SchoolAndItsSeries
import com.mboasikolopath.data.model.relationships.SeriesAndItsSchools
import com.mboasikolopath.data.model.relationships.SeriesSchool
import com.mboasikolopath.data.model.relationships.pairs.SeriesSchoolPair
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SeriesSchoolRepoImpl(
    private val seriesSchoolDao: SeriesSchoolDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SeriesSchoolRepo() {

    override suspend fun initData() = initSeriesSchoolData()

    init {
        appDataSource.downloadedSeriesSchools.observeForever {
            scope.launch { saveSeriesSchools(it) }
        }
    }

    private suspend fun saveSeriesSchools(seriesSchools: List<SeriesSchool>) =
        seriesSchoolDao.insertAll(*seriesSchools.toTypedArray())

    private suspend fun initSeriesSchoolData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SERIES_SCHOOLS))) {
            appDataSource.seriesSchools()
            appStorage.setLastSaved(DataKey.SERIES_SCHOOLS, ZonedDateTime.now())
        }
    }

    private suspend fun loadData(): List<SeriesSchoolPair> {
        initSeriesSchoolData()
        val data = seriesSchoolDao.findSeriesAndSchoolPairs()
        if (data.isNullOrEmpty()) {
            appStorage.clearLastSaved(DataKey.SERIES_JOBS)
            initSeriesSchoolData()
        }
        return data
    }

    override suspend fun getSeriesAndItsSchools(): List<SeriesAndItsSchools> = withContext(Dispatchers.IO) {
        val data = loadData()
        return@withContext if (!data.isNullOrEmpty()) SeriesSchool.groupSchools(data)
        else emptyList<SeriesAndItsSchools>()
    }

    override suspend fun getSchoolAndItsSeries(): List<SchoolAndItsSeries> = withContext(Dispatchers.IO) {
        val data = loadData()
        return@withContext if (!data.isNullOrEmpty()) SeriesSchool.groupSeries(data)
        else emptyList<SchoolAndItsSeries>()
    }

    override suspend fun findSchoolsBySeriesID(id: String) = withContext(Dispatchers.IO) {
        return@withContext seriesSchoolDao.findSchoolsBySeriesID(id)
    }

    override suspend fun findSeriesBySchoolID(id: Int) = withContext(Dispatchers.IO) {
        seriesSchoolDao.findSeriesBySchoolID(id)
    }
}