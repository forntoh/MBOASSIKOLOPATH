package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.NewsDao
import com.mboasikolopath.data.model.News
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class NewsRepoImpl(private val newsDao: NewsDao, private val appStorage: AppStorage, private val appDataSource: AppDataSource) : NewsRepo() {
    
    override suspend fun initData() = initNewsData()
    
    init {
        appDataSource.downloadedNews.observeForever {
            scope.launch { it?.let { saveNews(it) } }
        }
    }

    private suspend fun saveNews(news: List<News>) {
        newsDao.insertAll(*news.toTypedArray())
        appStorage.setLastSaved(DataKey.NEWS, ZonedDateTime.now())
    }

    private suspend fun initNewsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.NEWS), 1L)) {
            appDataSource.news()
        }
    }

    override suspend fun loadAll(): List<News> {
        initNewsData()
        return newsDao.loadAll()
    }

    override suspend fun findNewsByID(id: Int): News {
        return newsDao.findByNewsID(id)
    }

}