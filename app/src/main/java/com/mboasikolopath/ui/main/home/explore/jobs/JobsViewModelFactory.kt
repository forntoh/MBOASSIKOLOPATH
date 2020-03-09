package com.mboasikolopath.ui.main.home.explore.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mboasikolopath.data.repository.JobRepo
import com.mboasikolopath.data.repository.SpecialityRepo
import com.mboasikolopath.data.repository.relationships.SeriesJobRepo

class JobsViewModelFactory(private val jobRepo: JobRepo, private val seriesJobRepo: SeriesJobRepo, private val specialityRepo: SpecialityRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return JobsViewModel(jobRepo, seriesJobRepo, specialityRepo) as T
    }
}