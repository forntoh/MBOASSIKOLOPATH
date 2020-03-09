package com.mboasikolopath.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.model.News

@Dao
interface NewsDao: BaseDao<News> {

    @Query("SELECT * FROM News")
    fun loadAll(): List<News>

    @Query("SELECT * FROM News WHERE NewsID LIKE :id LIMIT 1")
    fun findByNewsID(id: Int): News
}