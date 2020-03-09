package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Speciality

class SpecialityRepoImplTest : SpecialityRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll() = listOf(
        Speciality(0, "Génie électrique"),
        Speciality(1, "Génie chimique et disciplines biomédicales"),
        Speciality(2, "Génie mécanique"),
        Speciality(3, "Gestion")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findBySpecialityID(id: Int) =
        loadAll().find { it.SpecialityID == id }!!
}