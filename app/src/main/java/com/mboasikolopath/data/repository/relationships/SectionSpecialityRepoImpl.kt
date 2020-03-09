package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.db.relationships.SectionSpecialityDao
import com.mboasikolopath.data.model.Speciality
import com.mboasikolopath.data.model.relationships.SectionSpeciality
import com.mboasikolopath.data.model.relationships.SectionSpecialityPair
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
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

    override suspend fun findSectionAndSpecialityPairs(): List<SectionSpecialityPair> {
        initSectionSpecialityData()
        return sectionSpecialityDao.findSectionAndSpecialityPairs()
    }

    override suspend fun findSpecialitiesBySectionID(id: Int): List<Speciality> {
        return sectionSpecialityDao.findSpecialitiesBySectionID(id)
    }
}