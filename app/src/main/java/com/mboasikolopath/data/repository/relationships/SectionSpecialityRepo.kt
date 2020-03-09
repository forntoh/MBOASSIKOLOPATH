package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.Speciality
import com.mboasikolopath.data.model.relationships.SectionSpecialityPair
import com.mboasikolopath.data.repository.BaseRepository

abstract class SectionSpecialityRepo: BaseRepository() {

    abstract suspend fun findSectionAndSpecialityPairs(): List<SectionSpecialityPair>

    abstract suspend fun findSpecialitiesBySectionID(id: Int): List<Speciality>
}