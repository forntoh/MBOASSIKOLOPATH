package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Certificate

class CertificateRepoImplTest : CertificateRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll() = listOf(
        Certificate("CAP", "ELEQ"),
        Certificate("BAC", "F2"),
        Certificate("CAP", "ELNI"),
        Certificate("BAC", "F3"),

        Certificate("CAP", "AIBC"),
        Certificate("BAC", "CI"),
        Certificate("CAP", "AICI"),
        Certificate("BAC", "F6-MIPE"),

        Certificate("CAP", "COOM"),
        Certificate("BAC", "BIJO"),
        Certificate("CAP", "MEFE"),
        Certificate("BAC", "F1"),

        Certificate("CAP", "ESCOM"),
        Certificate("BAC", "ACA"),
        Certificate("CAP", "ESFI"),
        Certificate("BAC", "ACC")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByCertificateID(id: String) =
        Certificate(id, id)


    override suspend fun findCertificateForSeriesOfCycle(seriesId: String, cycle: Int) =
        loadAll().find { it.SeriesID == seriesId }!!
}