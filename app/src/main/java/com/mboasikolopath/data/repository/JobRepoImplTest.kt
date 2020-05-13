package com.mboasikolopath.data.repository

import androidx.paging.DataSource
import com.mboasikolopath.data.model.Job

class JobRepoImplTest : JobRepo() {

    override suspend fun initData() = Unit
/*
    override suspend fun searchJob(query: String) = loadAll().filter { it.Name.toLowerCase().matches(Regex(".*${query.toLowerCase()}.+")) }

    override suspend fun loadAll() = listOf(
        Job(0, "Chaudronnier"),
        Job(1, "Soudeur"),
        Job(2, "Charpentier metallique"),
        Job(3, "Serrurier metallier"),
        Job(4, "Formulateur"),
        Job(5, "Aromaticien"),
        Job(6, "Assureur qualite en industrie"),
        Job(7, "Agent de laboratoire"),
        Job(8, "Depanneur"),
        Job(9, "Maintenancier energie"),
        Job(10, "Electromecanicien"),
        Job(0, "Chaudronnier"),
        Job(1, "Soudeur"),
        Job(2, "Charpentier metallique"),
        Job(3, "Serrurier metallier"),
        Job(4, "Formulateur"),
        Job(5, "Aromaticien"),
        Job(6, "Assureur qualite en industrie"),
        Job(7, "Agent de laboratoire"),
        Job(8, "Depanneur"),
        Job(9, "Maintenancier energie"),
        Job(10, "Electromecanicien")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByJobID(id: Int) = loadAll().find { it.JobID == id }!!*/

    override suspend fun loadAllPaged(): DataSource.Factory<Int, Job> {
        TODO("Not yet Implemented")
    }

    override suspend fun findByJobID(id: Int): Job {
        TODO("Not yet implemented")
    }

    override suspend fun searchJob(query: String): DataSource.Factory<Int, Job> {
        TODO("Not yet implemented")
    }
}