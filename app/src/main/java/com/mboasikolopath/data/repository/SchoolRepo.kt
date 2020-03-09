package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.School

abstract class SchoolRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<School>

    abstract suspend fun findBySchoolID(id: Int): School

    abstract suspend fun findSchoolsForLocality(id: Int): List<School>

    abstract suspend fun searchSchoolByName(query: String): List<School>
}