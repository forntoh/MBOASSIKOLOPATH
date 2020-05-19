package com.mboasikolopath.ui.main.home.explore.schools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SchoolRepo
import com.mboasikolopath.internal.runOnUiThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchoolsViewModel(private val schoolRepo: SchoolRepo, private val locationRepo: LocationRepo) : ViewModel() {

    private val pageSize = 15

    private val pageListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders (false)
        .setPageSize           (pageSize)
        .setPrefetchDistance   (pageSize)
        .setInitialLoadSizeHint(pageSize * 2)
        .setMaxSize            (pageSize * 8)
        .build()

    init {
        schoolRepo.scope = viewModelScope
        locationRepo.scope = viewModelScope

        viewModelScope.launch(Dispatchers.IO) {
            val factory: DataSource.Factory<Int, School> = schoolRepo.loadAllPaged()
            runOnUiThread {
                factory.toLiveData(pageListConfig).observeForever {
                    _schoolsLiveData.postValue(it)
                }
            }
        }
    }

    private val _schoolsLiveData = MutableLiveData<PagedList<School>>()
    val schoolsLiveData: LiveData<PagedList<School>>
        get() = _schoolsLiveData

    suspend fun getLocality(localityId: Int) = locationRepo.findRegionOfLocality(localityId)?.Name

    suspend fun searchSchoolByName(query: String) {
        val factory: DataSource.Factory<Int, School> = schoolRepo.searchSchoolByName(query)
        runOnUiThread {
            factory.toLiveData(pageListConfig).observeForever {
                _schoolsLiveData.postValue(it)
            }
        }
    }
}
