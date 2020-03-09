package com.mboasikolopath.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.R
import com.mboasikolopath.data.model.Section

class SectionRepoImplTest(private val context: Context) : SectionRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAll() = listOf(
        Section(0, "Sous-système francophone", "Sous-système francophone ${context.getString(R.string.lorem)}", 0),
        Section(1, "Sous-système anglophone", "Sous-système anglophone ${context.getString(R.string.lorem)}",  0),
        Section(2, "Sous-système bilingue","Sous-système bilingue ${context.getString(R.string.lorem)}",  0),

        Section(3, "Techniques Industrielles","Techniques Industrielles ${context.getString(R.string.lorem)}",  1),
        Section(4, "Sciences et Technologie du Tertiaire",  "Sciences et Technologie du Tertiaire ${context.getString(R.string.lorem)}",  1),

        Section(5, "Agriculture", "Agriculture ${context.getString(R.string.lorem)}",  2),
        Section(6, "Travaux publics","Travaux publics ${context.getString(R.string.lorem)}", 2),
        Section(7, "Administration pénitentiaire", "Administration pénitentiaire ${context.getString(R.string.lorem)}", 2)
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findBySectionID(id: Int) =
        loadAll().find { it.SectionID == id }!!

    override suspend fun findSectionsOfEducation(id: Int) =
        loadAll().filter { it.EducationID == id }
}