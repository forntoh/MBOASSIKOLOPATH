package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Education

abstract class EducationRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<Education>

    abstract suspend fun findByEducationID(id: Int): Education
}