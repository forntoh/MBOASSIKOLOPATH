package com.mboasikolopath.ui.main.home.explore.schools

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SchoolRepo
import com.mboasikolopath.internal.lazyDeferred
import com.mboasikolopath.internal.view.GenericListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SchoolsViewModel(private val schoolRepo: SchoolRepo, private val locationRepo: LocationRepo) : ViewModel() {

    init {
        schoolRepo.scope = viewModelScope
        locationRepo.scope = viewModelScope

        viewModelScope.launch(Dispatchers.IO) {
            val factory: DataSource.Factory<Int, School> = schoolRepo.loadAllPaged()

            val pagedListBuilder: LivePagedListBuilder<Int, School>  = LivePagedListBuilder(factory, 25)

            schoolsLiveData = pagedListBuilder.build()

        }
    }

    lateinit var schoolsLiveData: LiveData<PagedList<School>>

    suspend fun getLocality(localityId: Int) = locationRepo.findRegionOfLocality(localityId)?.Name

    val schools by lazyDeferred {
        schoolRepo.loadAll().map {
            GenericListItem(
                it.SchoolID.toString(),
                it.Name,
                locationRepo.findRegionOfLocality(it.LocaliteID)?.Name
            )
        }
    }

    suspend fun searchSchoolByName(query: String): List<GenericListItem> = withContext(Dispatchers.IO) {
        return@withContext schoolRepo.searchSchoolByName(query).map {
            GenericListItem(
                it.SchoolID.toString(),
                it.Name,
                locationRepo.findRegionOfLocality(it.LocaliteID)?.Name
            )
        }
    }
}
