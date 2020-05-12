package com.mboasikolopath.data.repository

import androidx.paging.DataSource
import com.mboasikolopath.data.db.SchoolDao
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SchoolRepoImpl(
    private val schoolDao: SchoolDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SchoolRepo() {

    override suspend fun initData() = initSchoolsData()

    init {
        appDataSource.downloadedSchools.observeForever {
            scope.launch { it?.let { saveSchools(it) } }
        }
    }

    private suspend fun saveSchools(schools: List<School>) {
        schoolDao.insertAll(*schools.toTypedArray())
        appStorage.setLastSaved(DataKey.SCHOOLS, ZonedDateTime.now())
    }

    private suspend fun initSchoolsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SCHOOLS))) {
            appDataSource.schools()
        }
    }

    override suspend fun loadAll() = withContext(Dispatchers.IO) {
        initSchoolsData()
        return@withContext schoolDao.loadAll()
    }

    override suspend fun loadAllPaged():DataSource.Factory<Int, School> = withContext(Dispatchers.IO) {
        schoolDao.loadAllPaged()
    }

    override suspend fun findBySchoolID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext schoolDao.findBySchoolID(id)
    }

    override suspend fun findSchoolsForLocality(id: Int) = withContext(Dispatchers.IO) {
        return@withContext schoolDao.findSchoolsForLocality(id)
    }

    override suspend fun searchSchoolByName(query: String): List<School> = withContext(Dispatchers.IO) {
        return@withContext schoolDao.searchSchoolByName("%$query%")
    }
}