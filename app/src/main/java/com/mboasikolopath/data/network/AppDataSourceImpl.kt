package com.mboasikolopath.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mboasikolopath.data.model.*
import com.mboasikolopath.data.model.relationships.*
import com.mboasikolopath.internal.NoConnectivityException
import com.mboasikolopath.utilities.Error
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.EOFException
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

    private val _downloadedNews = MutableLiveData<List<News>>()
    override val downloadedNews: LiveData<List<News>>
        get() = _downloadedNews

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

    override suspend fun certificateDebouches() =
        _downloadedCertificateDebouches.postValue(apiCover { apiService.certificateDebouches.awaitResponse() })

    override suspend fun sectionSpecialities() =
        _downloadedSectionSpecialities.postValue(apiCover { apiService.sectionSpecialities.awaitResponse() })

    override suspend fun seriesJobs() =
        _downloadedSeriesJobs.postValue(apiCover { apiService.seriesJobs.awaitResponse() })

    override suspend fun seriesSchools() =
        _downloadedSeriesSchools.postValue(apiCover { apiService.seriesSchools.awaitResponse() })

    override suspend fun seriesSubjectsTaught() =
        _downloadedSeriesSubjectsTaught.postValue(apiCover { apiService.seriesSubjectsTaught.awaitResponse() })

    override suspend fun arrondissements() =
        _downloadedArrondissements.postValue(apiCover { apiService.arrondissements.awaitResponse() })

    override suspend fun certificates() =
        _downloadedCertificates.postValue(apiCover { apiService.certificates.awaitResponse() })

    override suspend fun debouches() =
        _downloadedDebouches.postValue(apiCover { apiService.debouches.awaitResponse() })

    override suspend fun departements() =
        _downloadedDepartements.postValue(apiCover { apiService.departements.awaitResponse() })

    override suspend fun educations() =
        _downloadedEducations.postValue(apiCover { apiService.educations.awaitResponse() })

    override suspend fun jobs() =
        _downloadedJobs.postValue(apiCover { apiService.jobs.awaitResponse() })

    override suspend fun news() =
        _downloadedNews.postValue(apiCover { apiService.news.awaitResponse() })

    override suspend fun localities() =
        _downloadedLocalities.postValue(apiCover { apiService.localities.awaitResponse() })

    override suspend fun regions() =
        _downloadedRegions.postValue(apiCover { apiService.regions.awaitResponse() })

    override suspend fun schools() =
        _downloadedSchools.postValue(apiCover { apiService.schools.awaitResponse() })

    override suspend fun sections() =
        _downloadedSections.postValue(apiCover { apiService.sections.awaitResponse() })

    override suspend fun series() =
        _downloadedSeries.postValue(apiCover { apiService.series.awaitResponse() })

    override suspend fun specialities() =
        _downloadedSpecialities.postValue(apiCover { apiService.specialities.awaitResponse() })

    override suspend fun subjectsTaught() =
        _downloadedSubjectsTaught.postValue(apiCover { apiService.subjectsTaught.awaitResponse() })

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

    //==============================================================================================

    private suspend fun <T> apiCover(api: suspend () -> Response<T>): T? {
        try {
            val fetchedData = api.invoke()
            Log.d("API", fetchedData.toString())
            if (fetchedData.isSuccessful) return fetchedData.body()
        } catch (e: NoConnectivityException) {
            Log.e("NoConnectivityException", "No Internet", e)
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeout", "Could not connect to server", e)
        } catch (e: EOFException) {
            Log.e("EOFException", "${e.message}", e)
        }
        return null
    }
}