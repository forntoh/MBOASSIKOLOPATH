package com.mboasikolopath.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.model.Region

@Dao
interface RegionDao: BaseDao<Region> {

    @Query("SELECT * FROM Region")
    fun loadAll(): List<Region>

    @Query("SELECT * FROM Region WHERE RegionID LIKE :id LIMIT 1")
    fun findByRegionID(id: Int): Region

    @Query("SELECT COUNT(RegionID) FROM Region")
    suspend fun numberOfItems(): Int
}