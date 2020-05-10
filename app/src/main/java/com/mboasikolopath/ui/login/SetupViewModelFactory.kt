package com.mboasikolopath.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mboasikolopath.data.repository.*
import com.mboasikolopath.data.repository.relationships.*

class SetupViewModelFactory(
    private val locationRepo: LocationRepo,
    private val userRepo: UserRepo,

    private val deboucheRepo: DeboucheRepo,
    private val educationRepo: EducationRepo,
    private val jobRepo: JobRepo,
    private val specialityRepo: SpecialityRepo,
    private val subjectTaughtRepo: SubjectTaughtRepo,
    private val seriesRepo: SeriesRepo,
    private val certificateRepo: CertificateRepo,
    private val schoolRepo: SchoolRepo,
    private val sectionRepo: SectionRepo,

    private val deboucheSeriesRepo: DeboucheSeriesRepo,
    private val sectionSpecialityRepo: SectionSpecialityRepo,
    private val seriesJobRepo: SeriesJobRepo,
    private val seriesSchoolRepo: SeriesSchoolRepo,
    private val seriesSubjectTaughtRepo: SeriesSubjectTaughtRepo
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SetupViewModel(
            locationRepo,
            userRepo,
            deboucheRepo,
            educationRepo,
            jobRepo,
            specialityRepo,
            subjectTaughtRepo,
            seriesRepo,
            certificateRepo,
            schoolRepo,
            sectionRepo,
            deboucheSeriesRepo,
            sectionSpecialityRepo,
            seriesJobRepo,
            seriesSchoolRepo,
            seriesSubjectTaughtRepo
        ) as T
    }
}