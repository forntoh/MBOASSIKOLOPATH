package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.SubjectTaught

@Dao
interface SubjectTaughtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg subjectsTaught: SubjectTaught)

    @Delete
    fun delete(vararg subjectTaught: SubjectTaught)

    @Query("SELECT * FROM SubjectTaught")
    fun loadAll(): List<SubjectTaught>

    @Query("SELECT * FROM SubjectTaught WHERE SubjectTaughtID LIKE :id LIMIT 1")
    fun findBySubjectTaughtID(id: Int): SubjectTaught
}