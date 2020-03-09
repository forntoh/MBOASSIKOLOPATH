package com.mboasikolopath.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mboasikolopath.data.db.relationships.*
import com.mboasikolopath.data.model.*
import com.mboasikolopath.data.model.relationships.*

@Database(
    entities = [Arrondissement::class, Certificate::class, Debouche::class, Departement::class, Education::class, Job::class, Localite::class, Region::class, School::class, Section::class, Series::class, Speciality::class, SubjectTaught::class, User::class, CertificateDebouche::class, SectionSpeciality::class, SeriesJob::class, SeriesSchool::class, SeriesSubjectTaught::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun certificateDeboucheDao(): CertificateDeboucheDao
    abstract fun sectionSpecialityDao(): SectionSpecialityDao
    abstract fun seriesJobDao(): SeriesJobDao
    abstract fun seriesSchoolDao(): SeriesSchoolDao
    abstract fun seriesSubjectTaughtDao(): SeriesSubjectTaughtDao

    abstract fun arrondissementDao(): ArrondissementDao
    abstract fun certificateDao(): CertificateDao
    abstract fun deboucheDao(): DeboucheDao
    abstract fun departementDao(): DepartementDao
    abstract fun educationDao(): EducationDao
    abstract fun jobDao(): JobDao
    abstract fun localiteDao(): LocaliteDao
    abstract fun regionDao(): RegionDao
    abstract fun schoolDao(): SchoolDao
    abstract fun sectionDao(): SectionDao
    abstract fun seriesDao(): SeriesDao
    abstract fun specialityDao(): SpecialityDao
    abstract fun subjectTaughtDao(): SubjectTaughtDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "mboasikolopath.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}