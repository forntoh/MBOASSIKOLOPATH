package com.mboasikolopath.data.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mboasikolopath.data.model.*
import com.mboasikolopath.data.model.relationships.*
import com.mboasikolopath.data.pref.AppStorage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiService {

    @get:GET("pair/debouche-series")
    val debouchesSeries: Call<List<DeboucheSeries>>

    @get:GET("pair/section-specialities")
    val sectionSpecialities: Call<List<SectionSpeciality>>

    @get:GET("pair/series-jobs")
    val seriesJobs: Call<List<SeriesJob>>

    @get:GET("pair/series-schools")
    val seriesSchools: Call<List<SeriesSchool>>

    @get:GET("pair/series-subjects-taught")
    val seriesSubjectsTaught: Call<List<SeriesSubjectTaught>>


    @get:GET("arrondissements")
    val arrondissements: Call<List<Arrondissement>>

    @get:GET("certificates")
    val certificates: Call<List<Certificate>>

    @get:GET("debouches")
    val debouches: Call<List<Debouche>>

    @get:GET("departements")
    val departements: Call<List<Departement>>

    @get:GET("educations")
    val educations: Call<List<Education>>

    @get:GET("jobs")
    val jobs: Call<List<Job>>

    @get:GET("localities")
    val localities: Call<List<Localite>>


    @get:GET("news")
    val news: Call<List<News>>

    @get:GET("regions")
    val regions: Call<List<Region>>

    @get:GET("schools")
    val schools: Call<List<School>>

    @get:GET("sections")
    val sections: Call<List<Section>>

    @get:GET("series")
    val series: Call<List<Series>>

    @get:GET("specialities")
    val specialities: Call<List<Speciality>>

    @get:GET("subjects-taught")
    val subjectsTaught: Call<List<SubjectTaught>>

    @FormUrlEncoded
    @POST("login")
    fun loginAsync(@FieldMap user: Map<String, String>): Call<User>

    @FormUrlEncoded
    @POST("signup")
    fun signupAsync(@FieldMap user: Map<String, String>): Call<User>

    companion object {

            private const val BASE_URL = "https://mboasikolopath.herokuapp.com/public/api/"

        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor, appStorage: AppStorage): ApiService {
            val requestInterceptor = Interceptor { chain ->

                val originalRequest = chain.request()
                val original = originalRequest.url()

                val newUrl = original
                    .newBuilder()
                    .addQueryParameter("api_token", appStorage.loadUser()?.Token)
                    .build()

                val request = originalRequest
                    .newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Content-Type", "application/json")
                    .url(newUrl)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(RetryInterceptor())
                .retryOnConnectionFailure(true)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient.apply { dispatcher().maxRequests = 100 })
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
                .create(ApiService::class.java)
        }
    }
}


