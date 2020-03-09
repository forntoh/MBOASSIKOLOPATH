package com.mboasikolopath.ui.main.home.explore.schools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SchoolRepo

class SchoolsViewModelFactory(private val schoolRepo: SchoolRepo, private val locationRepo: LocationRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SchoolsViewModel(schoolRepo, locationRepo) as T
    }
}