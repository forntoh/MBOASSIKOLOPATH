package com.mboasikolopath.data.repository

import androidx.paging.DataSource
import com.mboasikolopath.data.model.School

abstract class SchoolRepo: BaseRepository() {

    abstract suspend fun findBySchoolID(id: Int): School

    abstract suspend fun findSchoolsForLocality(id: Int): List<School>

    abstract suspend fun searchSchoolByName(query: String): DataSource.Factory<Int, School>

    abstract suspend fun loadAllPaged(): DataSource.Factory<Int, School>
}