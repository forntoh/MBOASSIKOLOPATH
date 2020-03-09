package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.SubjectTaught

abstract class SubjectTaughtRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<SubjectTaught>

    abstract suspend fun findBySubjectTaughtID(id: Int): SubjectTaught
}