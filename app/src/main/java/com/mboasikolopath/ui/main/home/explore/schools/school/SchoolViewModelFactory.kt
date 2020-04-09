package com.mboasikolopath.ui.main.home.explore.schools.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SchoolRepo
import com.mboasikolopath.data.repository.relationships.SeriesSchoolRepo

class SchoolViewModelFactory(private val schoolRepo: SchoolRepo, private val locationRepo: LocationRepo, private val seriesSchoolRepo: SeriesSchoolRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SchoolViewModel(locationRepo, schoolRepo, seriesSchoolRepo) as T
    }
}