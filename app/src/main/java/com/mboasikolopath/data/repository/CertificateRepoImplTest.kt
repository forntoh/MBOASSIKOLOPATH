package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Certificate

class CertificateRepoImplTest : CertificateRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll() = listOf(
        Certificate("CAP", "ELEQ", "ELEQ"),
        Certificate("BAC", "F2", "F2"),
        Certificate("CAP", "ELNI", "ELNI"),
        Certificate("BAC", "F3", "F3"),

        Certificate("CAP", "AIBC", "AIBC"),
        Certificate("BAC", "CI", "CI"),
        Certificate("CAP", "AICI", "AICI"),
        Certificate("BAC", "F6-MIPE", "F6-MIPE"),

        Certificate("CAP", "COOM", "COOM"),
        Certificate("BAC", "BIJO", "BIJO"),
        Certificate("CAP", "MEFE", "MEFE"),
        Certificate("BAC", "F1", "F1"),

        Certificate("CAP", "ESCOM", "ESCOM"),
        Certificate("BAC", "ACA", "ACA"),
        Certificate("CAP", "ESFI", "ESFI"),
        Certificate("BAC", "ACC", "ACC")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByCertificateID(id: String) =
        Certificate(id, id, "")


    override suspend fun findCertificateForSeriesOfCycle(seriesId: String, cycle: Int) =
        loadAll().find { it.SeriesID == seriesId }!!
}