package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Section

class SectionRepoImplTest : SectionRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll() = listOf(
        Section(0, "Sous-système francophone", 0),
        Section(1, "Sous-système anglophone", 0),
        Section(2, "Sous-système bilingue", 0),

        Section(3, "Techniques Industrielles", 1),
        Section(4, "Sciences et Technologie du Tertiaire", 1),

        Section(5, "Agriculture", 2),
        Section(6, "Travaux publics", 2),
        Section(7, "Administration pénitentiaire", 2)
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findBySectionID(id: Int) =
        loadAll().find { it.SectionID == id }!!

    override suspend fun findSectionsOfEducation(id: Int) =
        loadAll().filter { it.EducationID == id }
}