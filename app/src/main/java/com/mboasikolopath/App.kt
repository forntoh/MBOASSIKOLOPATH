package com.mboasikolopath

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mboasikolopath.data.db.*
import com.mboasikolopath.data.db.relationships.*
import com.mboasikolopath.data.network.*
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.repository.*
import com.mboasikolopath.data.repository.relationships.*
import com.mboasikolopath.ui.login.SetupViewModelFactory
import com.mboasikolopath.ui.main.home.explore.jobs.JobsViewModelFactory
import com.mboasikolopath.ui.main.home.explore.schools.SchoolsViewModelFactory
import com.mboasikolopath.ui.main.home.explore.schools.school.SchoolViewModelFactory
import com.mboasikolopath.ui.main.home.explore.sector.SectorViewModelFactory
import com.mboasikolopath.ui.main.home.explore.series.SeriesViewModelFactory
import com.mboasikolopath.ui.main.profile.ProfileViewModelFactory
import com.tripl3dev.prettystates.StatesConfigFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

@Suppress("unused")
class App : MultiDexApplication(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        // Bind Database
        bind() from singleton { AppDatabase(instance()) }
        // Bind Data source
        bind<AppDataSource>() with singleton { AppDataSourceImpl(instance()) }
        // Bind App storage
        bind() from singleton { AppStorage(instance()) }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiService(instance(), instance()) }

        // Relationships
        bind<CertificateDeboucheDao>() with singleton { instance<AppDatabase>().certificateDeboucheDao() }
        //bind<CertificateDeboucheRepo>() with singleton { CertificateDeboucheRepoImplTest(instance(), instance()) }
        bind<CertificateDeboucheRepo>() with singleton { CertificateDeboucheRepoImpl(instance(), instance(), instance()) }

        bind<SectionSpecialityDao>() with singleton { instance<AppDatabase>().sectionSpecialityDao() }
        //bind<SectionSpecialityRepo>() with singleton { SectionSpecialityRepoImplTest(instance(), instance()) }
        bind<SectionSpecialityRepo>() with singleton { SectionSpecialityRepoImpl(instance(), instance(), instance()) }

        bind<SeriesJobDao>() with singleton { instance<AppDatabase>().seriesJobDao() }
        //bind<SeriesJobRepo>() with singleton { SeriesJobRepoImplTest(instance(), instance()) }
        bind<SeriesJobRepo>() with singleton { SeriesJobRepoImpl(instance(), instance(), instance()) }

        bind<SeriesSchoolDao>() with singleton { instance<AppDatabase>().seriesSchoolDao() }
        //bind<SeriesSchoolRepo>() with singleton { SeriesSchoolRepoImplTest(instance(), instance()) }
        bind<SeriesSchoolRepo>() with singleton { SeriesSchoolRepoImpl(instance(), instance(), instance()) }

        bind<SeriesSubjectTaughtDao>() with singleton { instance<AppDatabase>().seriesSubjectTaughtDao() }
        //bind<SeriesSubjectTaughtRepo>() with singleton { SeriesSubjectTaughtRepoImplTest(instance(), instance()) }
        bind<SeriesSubjectTaughtRepo>() with singleton { SeriesSubjectTaughtRepoImpl(instance(), instance(), instance()) }

        // Base
        bind<LocaliteDao>() with singleton { instance<AppDatabase>().localiteDao() }
        bind<ArrondissementDao>() with singleton { instance<AppDatabase>().arrondissementDao() }
        bind<DepartementDao>() with singleton { instance<AppDatabase>().departementDao() }
        bind<RegionDao>() with singleton { instance<AppDatabase>().regionDao() }
        //bind<LocationRepo>() with singleton { LocationRepoImplTest() }
        bind<LocationRepo>() with singleton { LocationRepoImpl(instance(), instance(), instance(), instance(), instance(), instance()) }

        bind<CertificateDao>() with singleton { instance<AppDatabase>().certificateDao() }
        //bind<CertificateRepo>() with singleton { CertificateRepoImplTest() }
        bind<CertificateRepo>() with singleton { CertificateRepoImpl(instance(), instance(), instance()) }

        bind<DeboucheDao>() with singleton { instance<AppDatabase>().deboucheDao() }
        //bind<DeboucheRepo>() with singleton { DeboucheRepoImplTest() }
        bind<DeboucheRepo>() with singleton { DeboucheRepoImpl(instance(), instance(), instance()) }

        bind<EducationDao>() with singleton { instance<AppDatabase>().educationDao() }
        //bind<EducationRepo>() with singleton { EducationRepoImplTest() }
        bind<EducationRepo>() with singleton { EducationRepoImpl(instance(), instance(), instance()) }

        bind<JobDao>() with singleton { instance<AppDatabase>().jobDao() }
        //bind<JobRepo>() with singleton { JobRepoImplTest() }
        bind<JobRepo>() with singleton { JobRepoImpl(instance(), instance(), instance()) }

        bind<SchoolDao>() with singleton { instance<AppDatabase>().schoolDao() }
        //bind<SchoolRepo>() with singleton { SchoolRepoImplTest() }
        bind<SchoolRepo>() with singleton { SchoolRepoImpl(instance(), instance(), instance()) }

        bind<SectionDao>() with singleton { instance<AppDatabase>().sectionDao() }
        //bind<SectionRepo>() with singleton { SectionRepoImplTest() }
        bind<SectionRepo>() with singleton { SectionRepoImpl(instance(), instance(), instance()) }

        bind<SeriesDao>() with singleton { instance<AppDatabase>().seriesDao() }
        //bind<SeriesRepo>() with singleton { SeriesRepoImplTest() }
        bind<SeriesRepo>() with singleton { SeriesRepoImpl(instance(), instance(), instance()) }

        bind<SpecialityDao>() with singleton { instance<AppDatabase>().specialityDao() }
        //bind<SpecialityRepo>() with singleton { SpecialityRepoImplTest() }
        bind<SpecialityRepo>() with singleton { SpecialityRepoImpl(instance(), instance(), instance()) }

        bind<SubjectTaughtDao>() with singleton { instance<AppDatabase>().subjectTaughtDao() }
        //bind<SubjectTaughtRepo>() with singleton { SubjectTaughtRepoImplTest() }
        bind<SubjectTaughtRepo>() with singleton { SubjectTaughtRepoImpl(instance(), instance(), instance()) }

        bind<UserDao>() with singleton { instance<AppDatabase>().userDao() }
        bind<UserRepo>() with singleton { UserRepoImpl(instance(), instance(), instance()) }

        bind<SchoolViewModelFactory>() with provider { SchoolViewModelFactory(instance(), instance(), instance()) }
        bind<SchoolsViewModelFactory>() with provider { SchoolsViewModelFactory(instance(), instance()) }
        bind<JobsViewModelFactory>() with provider { JobsViewModelFactory(instance(), instance(), instance()) }
        bind<SectorViewModelFactory>() with provider { SectorViewModelFactory(instance(), instance(), instance(), instance()) }
        bind<SeriesViewModelFactory>() with provider { SeriesViewModelFactory(instance(), instance(), instance(), instance(), instance(), instance(), instance()) }
        bind<ProfileViewModelFactory>() with provider { ProfileViewModelFactory(instance(), instance()) }
        bind<SetupViewModelFactory>() with provider { SetupViewModelFactory(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance()) }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AndroidThreeTen.init(this)
        StatesConfigFactory.intialize().initViews()
            .setDefaultEmptyLayout(R.layout.state_empty)
            .setDefaultLoadingLayout(R.layout.state_loading)
    }
}
