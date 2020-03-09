package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.SectionDao
import com.mboasikolopath.data.model.Section
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class SectionRepoImpl(
    private val sectionDao: SectionDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : SectionRepo() {

    override suspend fun initData() = initSectionsData()

    init {
        appDataSource.downloadedSections.observeForever {
            it?.let { scope.launch { saveSections(it) } }
        }
    }

    private suspend fun saveSections(sections: List<Section>) =
        sectionDao.insertAll(*sections.toTypedArray())

    private suspend fun initSectionsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.SECTIONS))) {
            appDataSource.sections()
            appStorage.setLastSaved(DataKey.SECTIONS, ZonedDateTime.now())
        }
    }

    override suspend fun loadAll(): List<Section> {
        initSectionsData()
        return sectionDao.loadAll()
    }

    override suspend fun findBySectionID(id: Int): Section {
        return sectionDao.findBySectionID(id)
    }

    override suspend fun findSectionsOfEducation(id: Int): List<Section> {
        return sectionDao.findSectionsOfEducation(id)
    }
}