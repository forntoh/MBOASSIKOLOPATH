package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.EducationDao
import com.mboasikolopath.data.model.Education
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private suspend fun saveEducations(educations: List<Education>) =
        educationDao.insertAll(*educations.toTypedArray())

    private suspend fun initEducationsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.EDUCATIONS))) {
            appDataSource.educations()
            appStorage.setLastSaved(DataKey.EDUCATIONS, ZonedDateTime.now())
        }
    }

    override suspend fun loadAll(): List<Education> = withContext(Dispatchers.IO) {
        initEducationsData()
        return@withContext educationDao.loadAll()
    }

    override suspend fun findByEducationID(id: Int): Education = withContext(Dispatchers.IO) {
        return@withContext educationDao.findByEducationID(id)
    }
}