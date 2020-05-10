package com.mboasikolopath.ui.main.home.explore.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mboasikolopath.data.repository.CertificateRepo
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SeriesRepo
import com.mboasikolopath.data.repository.relationships.DeboucheSeriesRepo
import com.mboasikolopath.data.repository.relationships.SeriesJobRepo
import com.mboasikolopath.data.repository.relationships.SeriesSchoolRepo
import com.mboasikolopath.data.repository.relationships.SeriesSubjectTaughtRepo

class SeriesViewModelFactory(
    private val seriesRepo: SeriesRepo,
    private val certificateRepo: CertificateRepo,
    private val deboucheSeriesRepo: DeboucheSeriesRepo,
    private val seriesSubjectTaughtRepo: SeriesSubjectTaughtRepo,
    private val seriesJobRepo: SeriesJobRepo,
    private val seriesSchoolRepo: SeriesSchoolRepo,
    private val locationRepo: LocationRepo
) : ViewModelProvider.NewInstanceFactory() {

    lateinit var seriesID: String
    var cycle = 0

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SeriesViewModel(seriesID, cycle, seriesRepo, certificateRepo, deboucheSeriesRepo, seriesSubjectTaughtRepo, seriesJobRepo, seriesSchoolRepo, locationRepo) as T
    }
}