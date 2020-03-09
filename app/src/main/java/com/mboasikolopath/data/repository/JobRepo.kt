package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Job

abstract class JobRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<Job>

    abstract suspend fun findByJobID(id: Int): Job

    abstract suspend fun searchJob(query: String): List<Job>
}