package com.mboasikolopath.ui.main.home.explore.schools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SchoolRepo
import com.mboasikolopath.internal.lazyDeferred
import com.mboasikolopath.internal.view.GenericListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SchoolsViewModel(private val schoolRepo: SchoolRepo, private val locationRepo: LocationRepo) : ViewModel() {

    init {
        schoolRepo.scope = viewModelScope
        locationRepo.scope = viewModelScope
    }

    private val schoolsDataSource by lazyDeferred {
        schoolRepo.loadAllPaged()
    }


    val sss by lazyDeferred {
        schoolsDataSource.await().toLiveData(25)
    }

    /*suspend fun getSchoolsLiveData() = withContext(Dispatchers.Default) {
        val factory: DataSource.Factory<Int, GenericListItem> = schoolRepo.loadAllPaged().map {
            GenericListItem(
                it.SchoolID.toString(),
                it.Name,
                runBlocking(this.coroutineContext) {
                    locationRepo.findRegionOfLocality(it.LocaliteID)?.Name
                }
            )
        }
        Log.d("PAGING", "Factory created")

        val pagedListBuilder: LivePagedListBuilder<Int, GenericListItem> = LivePagedListBuilder(factory, 25)
        Log.d("PAGING", "Builder created")

        pagedListBuilder.build()
    }*/

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
