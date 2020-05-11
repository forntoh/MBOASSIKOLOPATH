package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.SpecialityDao
import com.mboasikolopath.data.model.Speciality
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SpecialityRepoImpl(
    private val specialityDao: SpecialityDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SpecialityRepo() {

    override suspend fun initData() = initSpecialitiesData()

    init {
        appDataSource.downloadedSpecialities.observeForever {
            it?.let { scope.launch { saveSpecialities(it) } }
        }
    }

    private suspend fun initSpecialitiesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SPECIALITIES))) {
            appDataSource.specialities()
        }
    }

    private suspend fun saveSpecialities(specialities: List<Speciality>) {
        specialityDao.insertAll(*specialities.toTypedArray())
        appStorage.setLastSaved(DataKey.SPECIALITIES, ZonedDateTime.now())
    }

    override suspend fun loadAll(): List<Speciality> = withContext(Dispatchers.IO) {
        initSpecialitiesData()
        return@withContext specialityDao.loadAll()
    }

    override suspend fun findBySpecialityID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext specialityDao.findBySpecialityID(id)
    }
}