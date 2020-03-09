package com.mboasikolopath.ui.main.home.explore.sector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mboasikolopath.data.repository.EducationRepo
import com.mboasikolopath.data.repository.SectionRepo
import com.mboasikolopath.data.repository.SeriesRepo
import com.mboasikolopath.data.repository.relationships.SectionSpecialityRepo

class SectorViewModelFactory(
    private val educationRepo: EducationRepo,
    private val sectionRepo: SectionRepo,
    private val sectionSpecialityRepo: SectionSpecialityRepo,
    private val seriesRepo: SeriesRepo
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SectorViewModel(educationRepo, sectionRepo, sectionSpecialityRepo, seriesRepo) as T
    }
}