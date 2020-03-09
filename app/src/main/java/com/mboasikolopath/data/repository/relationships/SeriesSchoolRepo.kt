package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.SchoolAndItsSeries
import com.mboasikolopath.data.model.relationships.SeriesAndItsSchools
import com.mboasikolopath.data.repository.BaseRepository

abstract class SeriesSchoolRepo: BaseRepository() {

    abstract suspend fun getSeriesAndItsSchools(): List<SeriesAndItsSchools>

    abstract suspend fun getSchoolAndItsSeries(): List<SchoolAndItsSeries>

    abstract suspend fun findSchoolsBySeriesID(id: String): List<School>

    abstract suspend fun findSeriesBySchoolID(id: Int): List<Series>
}