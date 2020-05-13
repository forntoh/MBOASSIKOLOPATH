package com.mboasikolopath.ui.main.home.explore.schools

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SchoolRepo
import com.mboasikolopath.internal.runOnUiThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchoolsViewModel(private val schoolRepo: SchoolRepo, private val locationRepo: LocationRepo) : ViewModel() {

    private val pageListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(15)
        .setPageSize(30)
        .build()

    lateinit var lifecycleOwner: LifecycleOwner

    init {
        schoolRepo.scope = viewModelScope
        locationRepo.scope = viewModelScope

        viewModelScope.launch(Dispatchers.IO) {
            val factory: DataSource.Factory<Int, School> = schoolRepo.loadAllPaged()
            val pagedListBuilder: LivePagedListBuilder<Int, School>  = LivePagedListBuilder(factory, pageListConfig)
            runOnUiThread { pagedListBuilder.build().observe(lifecycleOwner, Observer { _schoolsLiveData.postValue(it) }) }
        }
    }

    private val _schoolsLiveData = MutableLiveData<PagedList<School>>()
    val schoolsLiveData: LiveData<PagedList<School>>
        get() = _schoolsLiveData

    suspend fun getLocality(localityId: Int) = locationRepo.findRegionOfLocality(localityId)?.Name

    suspend fun searchSchoolByName(query: String) {
        val factory: DataSource.Factory<Int, School> = schoolRepo.searchSchoolByName(query)
        val pagedListBuilder: LivePagedListBuilder<Int, School>  = LivePagedListBuilder(factory, pageListConfig)
        runOnUiThread { pagedListBuilder.build().observe(lifecycleOwner, Observer { _schoolsLiveData.postValue(it) }) }
    }
}
