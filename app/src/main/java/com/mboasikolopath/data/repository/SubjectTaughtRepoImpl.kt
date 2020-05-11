package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.SubjectTaughtDao
import com.mboasikolopath.data.model.SubjectTaught
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SubjectTaughtRepoImpl(
    private val subjectTaughtDao: SubjectTaughtDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SubjectTaughtRepo() {

    override suspend fun initData() = initSubjectsTaughtData()

    init {
        appDataSource.downloadedSubjectsTaught.observeForever {
            it?.let { scope.launch { saveSubjectsTaught(it) } }
        }
    }

    private suspend fun saveSubjectsTaught(subjectsTaught: List<SubjectTaught>) {
        subjectTaughtDao.insertAll(*subjectsTaught.toTypedArray())
        appStorage.setLastSaved(DataKey.SUBJECTS_TAUGHT, ZonedDateTime.now())
    }

    private suspend fun initSubjectsTaughtData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SUBJECTS_TAUGHT))) {
            appDataSource.subjectsTaught()
        }
    }

    override suspend fun loadAll() = withContext(Dispatchers.IO) {
        initSubjectsTaughtData()
        return@withContext subjectTaughtDao.loadAll()
    }

    override suspend fun findBySubjectTaughtID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext subjectTaughtDao.findBySubjectTaughtID(id)
    }
}