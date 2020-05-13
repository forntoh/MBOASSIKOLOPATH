package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.EducationDao
import com.mboasikolopath.data.model.Education
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.internal.runOnIoThread
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class EducationRepoImpl(
    private val educationDao: EducationDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : EducationRepo() {

    override suspend fun initData() = initEducationsData()

    init {
        appDataSource.downloadedEducations.observeForever {
            it?.let { scope.launch { saveEducations(it) } }
        }
    }

    private suspend fun saveEducations(educations: List<Education>) {
        educationDao.insertAll(*educations.toTypedArray())
        appStorage.setLastSaved(DataKey.EDUCATIONS, ZonedDateTime.now())
    }

    private suspend fun initEducationsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.EDUCATIONS))) {
            appDataSource.educations()
        }
    }

    override suspend fun loadAll(): List<Education> = runOnIoThread {
        initEducationsData()
        educationDao.loadAll()
    }

    override suspend fun findByEducationID(id: Int): Education = runOnIoThread {
        educationDao.findByEducationID(id)
    }
}