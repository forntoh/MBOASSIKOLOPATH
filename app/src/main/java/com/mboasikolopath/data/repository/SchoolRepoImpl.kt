package com.mboasikolopath.data.repository

import androidx.paging.DataSource
import com.mboasikolopath.data.db.SchoolDao
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.internal.runOnIoThread
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
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

    override suspend fun loadAllPaged(): DataSource.Factory<Int, School> = runOnIoThread {
        initSchoolsData()
        schoolDao.loadAllPaged()
    }

    override suspend fun findBySchoolID(id: Int) = runOnIoThread {
        schoolDao.findBySchoolID(id)
    }

    override suspend fun findSchoolsForLocality(id: Int) = runOnIoThread {
        schoolDao.findSchoolsForLocality(id)
    }

    override suspend fun searchSchoolByName(query: String): DataSource.Factory<Int, School> = runOnIoThread {
        schoolDao.searchSchoolByName("%$query%")
    }
}