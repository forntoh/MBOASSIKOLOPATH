package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.SchoolAndItsSeries
import com.mboasikolopath.data.model.relationships.SeriesAndItsSchools
import com.mboasikolopath.data.repository.SchoolRepo
import com.mboasikolopath.data.repository.SeriesRepo

class SeriesSchoolRepoImplTest(val seriesRepo: SeriesRepo, val schoolRepo: SchoolRepo) : SeriesSchoolRepo() {

    override suspend fun initData() = Unit

    override suspend fun getSeriesAndItsSchools(): List<SeriesAndItsSchools> {
        val list = mutableListOf<SeriesAndItsSchools>()

        for (i in 0..9) {

            val subList = mutableListOf<School>()
            for (j in 0..9) {
                subList.add(schoolRepo.findBySchoolID(j))
            }

            list.add(
                SeriesAndItsSchools(
                    seriesRepo.loadAll()[i],
                    subList
                )
            )
        }
        return list
    }

    override suspend fun getSchoolAndItsSeries(): List<SchoolAndItsSeries> {
        val list = mutableListOf<SchoolAndItsSeries>()

        for (i in 0..9) {

            val subList = mutableListOf<Series>()
            for (j in 0..9) {
                subList.add(seriesRepo.loadAll()[j])
            }

            list.add(
                SchoolAndItsSeries(
                    schoolRepo.findBySchoolID(i),
                    subList
                )
            )
        }
        return list
    }

    override suspend fun findSchoolsBySeriesID(id: String) = schoolRepo.loadAll()

    override suspend fun findSeriesBySchoolID(id: Int) = seriesRepo.loadAll()
}