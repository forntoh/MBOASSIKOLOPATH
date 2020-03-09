package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.Education

@Dao
interface EducationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg educations: Education)

    @Delete
    fun delete(vararg education: Education)

    @Query("SELECT * FROM Education")
    fun loadAll(): List<Education>

    @Query("SELECT * FROM Education WHERE EducationID LIKE :id LIMIT 1")
    fun findByEducationID(id: Int): Education
}