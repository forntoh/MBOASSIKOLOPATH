package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Education

class EducationRepoImplTest : EducationRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll(): List<Education> = listOf(
        Education(0, "General Education"),
        Education(1, "Technical Education"),
        Education(2, "Formation professionnelle")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByEducationID(id: Int) = loadAll().find { it.EducationID == id }!!
}