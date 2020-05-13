package com.mboasikolopath.data.repository

import androidx.paging.DataSource
import com.mboasikolopath.data.model.Job

abstract class JobRepo: BaseRepository() {

    abstract suspend fun loadAllPaged(): DataSource.Factory<Int, Job>

    abstract suspend fun findByJobID(id: Int): Job

    abstract suspend fun searchJob(query: String): DataSource.Factory<Int, Job>
}