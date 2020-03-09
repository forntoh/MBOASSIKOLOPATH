package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.Debouche

@Dao
interface DeboucheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg debouches: Debouche)

    @Delete
    fun delete(vararg debouche: Debouche)

    @Query("SELECT * FROM Debouche")
    fun loadAll(): List<Debouche>

    @Query("SELECT * FROM Debouche WHERE DeboucheID LIKE :id LIMIT 1")
    fun findByDeboucheID(id: Int): Debouche
}