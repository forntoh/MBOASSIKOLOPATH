package com.mboasikolopath.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mboasikolopath.data.model.*
import com.mboasikolopath.data.model.relationships.*
import com.mboasikolopath.internal.NoConnectivityException
import com.mboasikolopath.utilities.Error
import retrofit2.awaitResponse
import java.net.SocketTimeoutException

class AppDataSourceImpl(private val apiService: ApiService) : AppDataSource {

    private val _downloadedCertificateDebouches = MutableLiveData<List<CertificateDebouche>>()
    override val downloadedCertificateDebouches: LiveData<List<CertificateDebouche>>
        get() = _downloadedCertificateDebouches

    private val _downloadedSectionSpecialities = MutableLiveData<List<SectionSpeciality>>()
    override val downloadedSectionSpecialities: LiveData<List<SectionSpeciality>>
        get() = _downloadedSectionSpecialities

    private val _downloadedSeriesJobs = MutableLiveData<List<SeriesJob>>()
    override val downloadedSeriesJobs: LiveData<List<SeriesJob>>
        get() = _downloadedSeriesJobs

    private val _downloadedSeriesSchools = MutableLiveData<List<SeriesSchool>>()
    override val downloadedSeriesSchools: LiveData<List<SeriesSchool>>
        get() = _downloadedSeriesSchools

    private val _downloadedSeriesSubjectsTaught = MutableLiveData<List<SeriesSubjectTaught>>()
    override val downloadedSeriesSubjectsTaught: LiveData<List<SeriesSubjectTaught>>
        get() = _downloadedSeriesSubjectsTaught

    private val _downloadedArrondissements = MutableLiveData<List<Arrondissement>>()
    override val downloadedArrondissements: LiveData<List<Arrondissement>>
        get() = _downloadedArrondissements

    private val _downloadedCertificates = MutableLiveData<List<Certificate>>()
    override val downloadedCertificates: LiveData<List<Certificate>>
        get() = _downloadedCertificates

    private val _downloadedDebouches = MutableLiveData<List<Debouche>>()
    override val downloadedDebouches: LiveData<List<Debouche>>
        get() = _downloadedDebouches

    private val _downloadedDepartements = MutableLiveData<List<Departement>>()
    override val downloadedDepartements: LiveData<List<Departement>>
        get() = _downloadedDepartements
    private val _downloadedEducations = MutableLiveData<List<Education>>()
    override val downloadedEducations: LiveData<List<Education>>
        get() = _downloadedEducations

    private val _downloadedJobs = MutableLiveData<List<Job>>()
    override val downloadedJobs: LiveData<List<Job>>
        get() = _downloadedJobs

    private val _downloadedLocalities = MutableLiveData<List<Localite>>()
    override val downloadedLocalities: LiveData<List<Localite>>
        get() = _downloadedLocalities

    private val _downloadedRegions = MutableLiveData<List<Region>>()
    override val downloadedRegions: LiveData<List<Region>>
        get() = _downloadedRegions

    private val _downloadedSchools = MutableLiveData<List<School>>()
    override val downloadedSchools: LiveData<List<School>>
        get() = _downloadedSchools

    private val _downloadedSections = MutableLiveData<List<Section>>()
    override val downloadedSections: LiveData<List<Section>>
        get() = _downloadedSections

    private val _downloadedSeries = MutableLiveData<List<Series>>()
    override val downloadedSeries: LiveData<List<Series>>
        get() = _downloadedSeries

    private val _downloadedSpecialities = MutableLiveData<List<Speciality>>()
    override val downloadedSpecialities: LiveData<List<Speciality>>
        get() = _downloadedSpecialities

    private val _downloadedSubjectsTaught = MutableLiveData<List<SubjectTaught>>()
    override val downloadedSubjectsTaught: LiveData<List<SubjectTaught>>
        get() = _downloadedSubjectsTaught

    private val _downloadedUser = MutableLiveData<User>()
    override val downloadedUser: LiveData<User>
        get() = _downloadedUser

    override suspend fun certificateDebouches() {
        try {
            val fetchedData = apiService.certificateDebouches.awaitResponse()
            if (fetchedData.isSuccessful)
                _downloadedCertificateDebouches.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun sectionSpecialities() {
        try {
            val fetchedData = apiService.sectionSpecialities.awaitResponse()
            if (fetchedData.isSuccessful)
                _downloadedSectionSpecialities.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun seriesJobs() {
        try {
            val fetchedData = apiService.seriesJobs.awaitResponse()
            if (fetchedData.isSuccessful)
                _downloadedSeriesJobs.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun seriesSchools() {
        try {
            val fetchedData = apiService.seriesSchools.awaitResponse()
            if (fetchedData.isSuccessful)
                _downloadedSeriesSchools.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun seriesSubjectsTaught() {
        try {
            val fetchedData = apiService.seriesSubjectsTaught.awaitResponse()
            if (fetchedData.isSuccessful)
                _downloadedSeriesSubjectsTaught.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun arrondissements() {
        try {
            val fetchedData = apiService.arrondissements.awaitResponse()
            _downloadedArrondissements.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun certificates() {
        try {
            val fetchedData = apiService.certificates.awaitResponse()
            _downloadedCertificates.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun debouches() {
        try {
            val fetchedData = apiService.debouches.awaitResponse()
            if (fetchedData.isSuccessful)
            _downloadedDebouches.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun departements() {
        try {
            val fetchedData = apiService.departements.awaitResponse()
            _downloadedDepartements.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun educations() {
        try {
            val fetchedData = apiService.educations.awaitResponse()
            if (fetchedData.isSuccessful)
            _downloadedEducations.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun jobs() {
        try {
            val fetchedData = apiService.jobs.awaitResponse()
            if (fetchedData.isSuccessful)
            _downloadedJobs.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun localities() {
        try {
            val fetchedData = apiService.localities.awaitResponse()
            _downloadedLocalities.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun regions() {
        try {
            val fetchedData = apiService.regions.awaitResponse()
            _downloadedRegions.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun schools() {
        try {
            val fetchedData = apiService.schools.awaitResponse()
            _downloadedSchools.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun sections() {
        try {
            val fetchedData = apiService.sections.awaitResponse()
            _downloadedSections.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun series() {
        try {
            val fetchedData = apiService.series.awaitResponse()
            _downloadedSeries.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun specialities() {
        try {
            val fetchedData = apiService.specialities.awaitResponse()
            if (fetchedData.isSuccessful)
            _downloadedSpecialities.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun subjectsTaught() {
        try {
            val fetchedData = apiService.subjectsTaught.awaitResponse()
            if (fetchedData.isSuccessful)
            _downloadedSubjectsTaught.postValue(fetchedData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun login(user: User) {
        try {
            val fetchedData = apiService.loginAsync(user.toMap()).awaitResponse()
            if (fetchedData.isSuccessful)
                _downloadedUser.postValue(fetchedData.body())
            else {
                val error = Gson().fromJson(fetchedData.errorBody()?.string(), Error::class.java)
                _downloadedUser.postValue(User(error.error))
            }
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }

    override suspend fun signup(user: User) {
        try {
            val fetchedData = apiService.signupAsync(user.toMap()).awaitResponse()
            if (fetchedData.isSuccessful)
                _downloadedUser.postValue(fetchedData.body())
            else {
                val err = Gson().fromJson(fetchedData.errorBody()?.string(), Error::class.java)
                _downloadedUser.postValue(User(err.error))
            }
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        }
    }
}