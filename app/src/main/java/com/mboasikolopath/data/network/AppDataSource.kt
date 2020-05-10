package com.mboasikolopath.data.network

import androidx.lifecycle.LiveData
import com.mboasikolopath.data.model.*
import com.mboasikolopath.data.model.relationships.*

interface AppDataSource {

    val downloadedDebouchesSeries: LiveData<List<DeboucheSeries>>
    val downloadedSectionSpecialities: LiveData<List<SectionSpeciality>>
    val downloadedSeriesJobs: LiveData<List<SeriesJob>>
    val downloadedSeriesSchools: LiveData<List<SeriesSchool>>
    val downloadedSeriesSubjectsTaught: LiveData<List<SeriesSubjectTaught>>

    val downloadedArrondissements: LiveData<List<Arrondissement>>
    val downloadedCertificates: LiveData<List<Certificate>>
    val downloadedDebouches: LiveData<List<Debouche>>
    val downloadedDepartements: LiveData<List<Departement>>
    val downloadedEducations: LiveData<List<Education>>
    val downloadedJobs: LiveData<List<Job>>
    val downloadedNews: LiveData<List<News>>
    val downloadedLocalities: LiveData<List<Localite>>
    val downloadedRegions: LiveData<List<Region>>
    val downloadedSchools: LiveData<List<School>>
    val downloadedSections: LiveData<List<Section>>
    val downloadedSeries: LiveData<List<Series>>
    val downloadedSpecialities: LiveData<List<Speciality>>
    val downloadedSubjectsTaught: LiveData<List<SubjectTaught>>
    val downloadedUser: LiveData<User>


    suspend fun certificateDebouches()
    suspend fun sectionSpecialities()
    suspend fun seriesJobs()
    suspend fun seriesSchools()
    suspend fun seriesSubjectsTaught()

    suspend fun arrondissements()
    suspend fun certificates()
    suspend fun debouches()
    suspend fun departements()
    suspend fun educations()
    suspend fun jobs()
    suspend fun news()
    suspend fun localities()
    suspend fun regions()
    suspend fun schools()
    suspend fun sections()
    suspend fun series()
    suspend fun specialities()
    suspend fun subjectsTaught()
    suspend fun login(user: User)
    suspend fun signup(user: User)
}