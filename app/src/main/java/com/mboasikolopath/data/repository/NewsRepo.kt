package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.News

abstract class NewsRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<News>

    abstract suspend fun findNewsByID(id: Int): News
}