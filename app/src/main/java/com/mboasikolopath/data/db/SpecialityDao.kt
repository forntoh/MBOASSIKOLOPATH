package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.Speciality

@Dao
interface SpecialityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg specialities: Speciality)

    @Delete
    fun delete(vararg speciality: Speciality)

    @Query("SELECT * FROM Speciality")
    fun loadAll(): List<Speciality>

    @Query("SELECT * FROM Speciality WHERE SpecialityID LIKE :id LIMIT 1")
    fun findBySpecialityID(id: Int): Speciality
}