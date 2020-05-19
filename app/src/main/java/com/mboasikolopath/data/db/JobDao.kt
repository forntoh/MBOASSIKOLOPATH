package com.mboasikolopath.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.model.Job

@Dao
interface JobDao: BaseDao<Job> {

    @Query("SELECT * FROM Job")
    fun loadAllPaged(): DataSource.Factory<Int, Job>

    @Query("SELECT * FROM Job WHERE JobID LIKE :id LIMIT 1")
    fun findByJobID(id: Int): Job

    @Query("SELECT * FROM Job WHERE LOWER(Name) LIKE LOWER(:query)")
    fun searchJob(query: String): DataSource.Factory<Int, Job>
}