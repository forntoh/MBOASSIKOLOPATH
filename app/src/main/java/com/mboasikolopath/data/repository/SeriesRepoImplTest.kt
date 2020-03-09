package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Series

class SeriesRepoImplTest : SeriesRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll() = listOf(
        Series("ELEQ", "Électricité d’équipement", 1, 0),
        Series("F2", "Électronique", 2, 0),
        Series("ELNI", "Électronique", 1, 0),
        Series("F3", "Électrotechnique", 2, 0),

        Series("AIBC", "Aide Biochimiste", 1, 1),
        Series("CI", "Chimiste Industriel", 2, 1),
        Series("AICI", "Aide Chimiste Industriel", 1, 1),
        Series("F6-MIPE", "Génie chimique, option : mine et pétrole", 2, 1),

        Series("COOM", "Construction en ouvrages métalliques", 1, 2),
        Series("BIJO", "Bijouterie-Joaillerie", 2, 2),
        Series("MEFE", "Métaux en Feuilles", 1, 2),
        Series("F1", "Fabrication Mécanique", 2, 2),

        Series("ESCOM", "Employé des sciences comptables", 1, 3),
        Series("ACA", "Action Communication Administrative", 2, 3),
        Series("ESFI", "Employé des services financiers", 1, 3),
        Series("ACC", "Action et Communication Commerciale", 2, 3)
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findBySeriesID(id: String) =
        loadAll().find { it.SeriesID == id }!!

    override suspend fun findSeriesOfSpeciality(id: Int) =
        loadAll().filter { it.SpecialityID == id }

    override suspend fun findSeriesOfSpecialityOfCycle(id: Int, cycle: Int): List<Series> =
        loadAll().filter { it.SpecialityID == id && it.Cycle == cycle }
}