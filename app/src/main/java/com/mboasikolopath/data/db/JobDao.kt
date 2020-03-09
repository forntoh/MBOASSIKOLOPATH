package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.Job

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg jobs: Job)

    @Delete
    fun delete(vararg job: Job)

    @Query("SELECT * FROM Job")
    fun loadAll(): List<Job>

    @Query("SELECT * FROM Job WHERE JobID LIKE :id LIMIT 1")
    fun findByJobID(id: Int): Job

    @Query("SELECT * FROM Job WHERE LOWER(Name) LIKE LOWER(:query)")
    fun searchJob(query: String): List<Job>
}