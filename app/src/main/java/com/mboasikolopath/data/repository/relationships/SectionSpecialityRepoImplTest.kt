package com.mboasikolopath.data.repository.relationships

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Speciality
import com.mboasikolopath.data.model.relationships.SectionSpeciality
import com.mboasikolopath.data.model.relationships.pairs.SectionSpecialityPair
import com.mboasikolopath.data.repository.SectionRepo
import com.mboasikolopath.data.repository.SpecialityRepo

class SectionSpecialityRepoImplTest(val sectionRepo: SectionRepo, val specialityRepo: SpecialityRepo) : SectionSpecialityRepo() {

    override suspend fun initData() = Unit

    override suspend fun findSectionAndSpecialityPairs(): List<SectionSpecialityPair> {
        val list = mutableListOf<SectionSpecialityPair>()
        for (i in 0..7) {
            for (j in 0..3) {
                list.add(
                    SectionSpecialityPair()
                        .apply {
                        Section = sectionRepo.findBySectionID(i)
                        Speciality = specialityRepo.findBySpecialityID(j)
                    }
                )
            }
        }
        return list.apply { Log.d(this[0]::class.java.name, Gson().toJson(this.map { SectionSpeciality(it.Section.SectionID, it.Speciality.SpecialityID) })) }
    }

    override suspend fun findSpecialitiesBySectionID(id: Int): List<Speciality> {
        findSectionAndSpecialityPairs()
        return specialityRepo.loadAll()
    }
}