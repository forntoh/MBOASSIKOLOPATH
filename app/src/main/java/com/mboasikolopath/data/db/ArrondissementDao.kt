package com.mboasikolopath.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.model.Arrondissement

@Dao
interface ArrondissementDao: BaseDao<Arrondissement> {

    @Query("SELECT * FROM Arrondissement")
    fun loadAll(): List<Arrondissement>

    @Query("SELECT * FROM Arrondissement WHERE ArrondissementID LIKE :id LIMIT 1")
    fun findByArrondissementID(id: Int): Arrondissement

    @Query("SELECT * FROM Arrondissement WHERE DepartementID LIKE :id")
    fun findArrondissementsOfDepartement(id: Int): List<Arrondissement>

    @Query("SELECT COUNT(ArrondissementID) FROM Arrondissement")
    suspend fun numberOfItems(): Int
}