package com.mboasikolopath.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mboasikolopath.data.model.User
import com.mboasikolopath.data.repository.*
import com.mboasikolopath.data.repository.relationships.*
import com.mboasikolopath.internal.lazyDeferred
import kotlinx.coroutines.delay

class SetupViewModel(
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
) : ViewModel() {

    init {
        userRepo.scope = viewModelScope
        locationRepo.scope = viewModelScope
        
        deboucheRepo.scope = viewModelScope
        educationRepo.scope = viewModelScope
        jobRepo.scope = viewModelScope
        specialityRepo.scope = viewModelScope
        subjectTaughtRepo.scope = viewModelScope
        seriesRepo.scope = viewModelScope
        certificateRepo.scope = viewModelScope
        schoolRepo.scope = viewModelScope
        sectionRepo.scope = viewModelScope
        
        deboucheSeriesRepo.scope = viewModelScope
        sectionSpecialityRepo.scope = viewModelScope
        seriesJobRepo.scope = viewModelScope
        seriesSchoolRepo.scope = viewModelScope
        seriesSubjectTaughtRepo.scope = viewModelScope
    }

    suspend fun downloadLocations() = locationRepo.initData()

    suspend fun populateDatabase() {
        val time = 2000L; delay(time)
        educationRepo.initData(); delay(time)
        specialityRepo.initData(); delay(time)
        deboucheRepo.initData(); delay(time)
        jobRepo.initData(); delay(time)
        subjectTaughtRepo.initData(); delay(time)

        sectionRepo.initData(); delay(time)
        seriesRepo.initData(); delay(time)
        certificateRepo.initData(); delay(time)
        schoolRepo.initData(); delay(time)

        sectionSpecialityRepo.initData(); delay(time)
        deboucheSeriesRepo.initData(); delay(time)
        seriesJobRepo.initData(); delay(time)
        seriesSubjectTaughtRepo.initData(); delay(time)
        seriesSchoolRepo.initData()
    }

    suspend fun getUser() = userRepo.getUser()

    fun getUserAsync() = userRepo.getUserAsync()

    suspend fun login(user: User) = userRepo.login(user)

    suspend fun signup(user: User) = userRepo.signup(user)

    val regions by lazyDeferred {
        locationRepo.loadAllRegions()
    }

    suspend fun findDepartementsOfRegion(id: Int) =
        locationRepo.findDepartementsOfRegion(id)

    suspend fun findArrondissementsOfDepartement(id: Int) =
        locationRepo.findArrondissementsOfDepartement(id)

    suspend fun findLocalitesOfArrondissement(id: Int) =
        locationRepo.findLocalitesOfArrondissement(id)

}