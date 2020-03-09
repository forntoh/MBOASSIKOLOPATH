package com.mboasikolopath.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.model.Localite

@Dao
interface LocaliteDao: BaseDao<Localite> {

    @Query("SELECT * FROM Localite")
    fun loadAll(): List<Localite>

    @Query("SELECT * FROM Localite WHERE LocaliteID LIKE :id LIMIT 1")
    fun findByLocaliteID(id: Int): Localite

    @Query("SELECT * FROM Localite WHERE ArrondissementID LIKE :id")
    fun findLocalitesOfArrondissement(id: Int): List<Localite>

    @Query("SELECT COUNT(LocaliteID) FROM Localite")
    suspend fun numberOfItems(): Int
}