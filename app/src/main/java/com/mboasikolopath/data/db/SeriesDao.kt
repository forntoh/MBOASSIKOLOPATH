package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.Series

@Dao
interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg series: Series)

    @Delete
    fun delete(vararg series: Series)

    @Query("SELECT * FROM Series")
    fun loadAll(): List<Series>

    @Query("SELECT * FROM Series WHERE SeriesID LIKE :id LIMIT 1")
    fun findBySeriesID(id: String): Series

    @Query("SELECT * FROM Series WHERE SpecialityID LIKE :id")
    fun findSeriesOfSpeciality(id: Int): List<Series>
}