package com.mboasikolopath.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.R
import com.mboasikolopath.data.model.Education

class EducationRepoImplTest(val context: Context) : EducationRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll(): List<Education> = listOf(
        Education(0, "General Education", "General Education ${context.getString(R.string.lorem)}"),
        Education(1, "Technical Education", "Technical Education ${context.getString(R.string.lorem)}"),
        Education(2, "Formation professionnelle", "Formation professionnelle ${context.getString(R.string.lorem)}")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByEducationID(id: Int) = loadAll().find { it.EducationID == id }!!
}