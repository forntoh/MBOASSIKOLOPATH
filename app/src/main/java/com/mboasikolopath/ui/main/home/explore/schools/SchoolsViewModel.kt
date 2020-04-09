package com.mboasikolopath.ui.main.home.explore.schools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
