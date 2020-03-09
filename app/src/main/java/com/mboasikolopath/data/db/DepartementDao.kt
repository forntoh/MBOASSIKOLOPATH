package com.mboasikolopath.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.model.Departement

@Dao
interface DepartementDao: BaseDao<Departement> {

    @Query("SELECT * FROM Departement")
    fun loadAll(): List<Departement>

    @Query("SELECT * FROM Departement WHERE DepartementID LIKE :id LIMIT 1")
    fun findByDepartementID(id: Int): Departement

    @Query("SELECT * FROM Departement WHERE RegionID LIKE :id")
    fun findDepartementsOfRegion(id: Int): List<Departement>

    @Query("SELECT COUNT(DepartementID) FROM Departement")
    suspend fun numberOfItems(): Int
}