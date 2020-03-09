package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.db.relationships.SectionSpecialityDao
import com.mboasikolopath.data.model.relationships.SectionSpeciality
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class SectionSpecialityRepoImpl(
    private val sectionSpecialityDao: SectionSpecialityDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SectionSpecialityRepo() {

    override suspend fun initData() = initSectionSpecialityData()

    init {
        appDataSource.downloadedSectionSpecialities.observeForever {
            scope.launch { saveSectionSpecialities(it) }
        }
    }

    private suspend fun saveSectionSpecialities(sectionSpecialities: List<SectionSpeciality>) =
        sectionSpecialityDao.insertAll(*sectionSpecialities.toTypedArray())

    private suspend fun initSectionSpecialityData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SECTION_SPECIALITIES))) {
            appDataSource.sectionSpecialities()
            appStorage.setLastSaved(DataKey.SECTION_SPECIALITIES, ZonedDateTime.now())
        }
    }

    override suspend fun findSectionAndSpecialityPairs() = withContext(Dispatchers.IO) {
        initSectionSpecialityData()
        return@withContext sectionSpecialityDao.findSectionAndSpecialityPairs()
    }

    override suspend fun findSpecialitiesBySectionID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext sectionSpecialityDao.findSpecialitiesBySectionID(id)
    }
}