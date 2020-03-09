package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Debouche

class DeboucheRepoImplTest : DeboucheRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll(): List<Debouche> = listOf(
        Debouche(0, "Auto-emploi"),
        Debouche(1, "Information et communication"),
        Debouche(2, "Défense"),
        Debouche(3, "Transport"),
        Debouche(4, "Science du vivant"),
        Debouche(5, "Sécurité")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByDeboucheID(id: Int) = loadAll().find { it.DeboucheID == id }!!
}