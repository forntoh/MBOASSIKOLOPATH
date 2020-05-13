package com.mboasikolopath.data.repository

import androidx.paging.DataSource
import com.mboasikolopath.data.model.School

class SchoolRepoImplTest : SchoolRepo() {

    override suspend fun initData() = Unit

    override suspend fun findBySchoolID(id: Int): School {
        TODO("Not yet implemented")
    }

    override suspend fun findSchoolsForLocality(id: Int): List<School> {
        TODO("Not yet implemented")
    }

    override suspend fun searchSchoolByName(query: String): DataSource.Factory<Int, School> {
        TODO("Not yet implemented")
    }

    override suspend fun loadAllPaged(): DataSource.Factory<Int, School> {
        TODO("Not yet implemented")
    }

/*
    override suspend fun searchSchoolByName(query: String) = loadAll().filter {
        it.Name.toLowerCase().matches(Regex(".*${query.toLowerCase()}.+"))
    }

    override suspend fun loadAll() = listOf(
        School(0, "LYCEE TECHNIQUE DE NGAOUNDAL", 0),
        School(1, "CETIC DE MAYO BALEO", 1),

        School(2, "CETIC DE LEMBE YEZOUM", 2),
        School(3, "CETIC DE NGOKSA", 3),

        School(4, "CETIC DE GARI-GOMBO", 4),
        School(5, "CETIC DE BAGOFIT", 5),

        School(6, "CETIC DE BOGO", 6),
        School(7, "CETIC DE YOUAYE-BLAM LAALE", 7),

        School(8, "CETIC DE DIBOMBARI", 8),
        School(9, "CETIC DE KOUEDJOU CARREFOUR", 9)
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findBySchoolID(id: Int) =
        loadAll().find { it.SchoolID == id }!!

    override suspend fun findSchoolsForLocality(id: Int) =
        loadAll().filter { it.LocaliteID == id }*/
}