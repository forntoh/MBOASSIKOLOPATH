package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.db.relationships.SeriesSubjectTaughtDao
import com.mboasikolopath.data.model.relationships.SeriesSubjectTaught
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.internal.runOnIoThread
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
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

    override suspend fun findSeriesAndSubjectTaughtPairs() = runOnIoThread {
        initSeriesSubjectTaughtData()
        seriesSubjectTaughtDao.findSeriesAndSubjectTaughtPairs()
    }

    override suspend fun findSubjectsTaughtBySeriesID(id: String) = runOnIoThread {
        seriesSubjectTaughtDao.findSubjectsTaughtBySeriesID(id)
    }
}