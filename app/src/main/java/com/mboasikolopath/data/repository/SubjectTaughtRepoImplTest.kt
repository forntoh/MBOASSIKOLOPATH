package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.SubjectTaught

class SubjectTaughtRepoImplTest : SubjectTaughtRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll() = listOf(
        SubjectTaught(0, "Presentation generale du metier et de la form..."),
        SubjectTaught(1, "Qualite, Hygiene, Securite et Environnement"),
        SubjectTaught(2, "Entrepreneuriat"),
        SubjectTaught(3, "Technologie"),
        SubjectTaught(4, "Circuits Electriques")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findBySubjectTaughtID(id: Int) =
        loadAll().find { it.SubjectTaughtID == id }!!
}