package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.db.relationships.SeriesSubjectTaughtDao
import com.mboasikolopath.data.model.relationships.SeriesSubjectTaught
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SeriesSubjectTaughtRepoImpl(
    private val seriesSubjectTaughtDao: SeriesSubjectTaughtDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SeriesSubjectTaughtRepo() {

    override suspend fun initData() = initSeriesSubjectTaughtData()

    init {
        appDataSource.downloadedSeriesSubjectsTaught.observeForever {
            scope.launch { saveSeriesSubjectsTaught(it) }
        }
    }

    private suspend fun saveSeriesSubjectsTaught(seriesSubjectsTaught: List<SeriesSubjectTaught>) {
        seriesSubjectTaughtDao.insertAll(*seriesSubjectsTaught.toTypedArray())
        appStorage.setLastSaved(DataKey.SERIES_SUBJECTS_TAUGHT, ZonedDateTime.now())
    }

    private suspend fun initSeriesSubjectTaughtData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SERIES_SUBJECTS_TAUGHT))) {
            appDataSource.seriesSubjectsTaught()
        }
    }

    override suspend fun findSeriesAndSubjectTaughtPairs() = withContext(Dispatchers.IO) {
        initSeriesSubjectTaughtData()
        return@withContext seriesSubjectTaughtDao.findSeriesAndSubjectTaughtPairs()
    }

    override suspend fun findSubjectsTaughtBySeriesID(id: String) = withContext(Dispatchers.IO) {
        return@withContext seriesSubjectTaughtDao.findSubjectsTaughtBySeriesID(id)
    }
}