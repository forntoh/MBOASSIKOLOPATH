package com.mboasikolopath.ui.main.home.explore.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mboasikolopath.data.repository.NewsRepo
import com.mboasikolopath.internal.view.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsViewModel(private val newsRepo: NewsRepo) : ViewModel() {

    init {
        newsRepo.scope = viewModelScope
    }

    suspend fun getNews() = withContext(Dispatchers.IO) {
        return@withContext newsRepo.loadAll().map {
            NewsItem(it.NewsID, it.Title, it.Thumbnail, it.Body)
        }
    }

    suspend fun findNewsByID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext newsRepo.findNewsByID(id)
    }

}
