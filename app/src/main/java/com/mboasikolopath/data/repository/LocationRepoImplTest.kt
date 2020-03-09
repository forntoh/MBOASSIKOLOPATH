package com.mboasikolopath.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Arrondissement
import com.mboasikolopath.data.model.Departement
import com.mboasikolopath.data.model.Localite
import com.mboasikolopath.data.model.Region

class LocationRepoImplTest : LocationRepo() {

    override suspend fun initData() = Unit

    override suspend fun loadAllLocalites() = listOf(
        Localite(0, "NGAOUNDAL", 0),
        Localite(1, "MAYO-BALEO", 1),

        Localite(2, "LEMBE-YEZOUM", 2),
        Localite(3, "NGOKSA", 3),

        Localite(4, "GARI-GOMBO", 4),
        Localite(5, "BAGOFIT", 5),

        Localite(6, "BOGO", 6),
        Localite(7, "YOUAYE", 7),

        Localite(8, "DIBOMBARI", 8),
        Localite(9, "KOUÉDJOU", 9)
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByLocaliteID(id: Int) =
        loadAllLocalites().find { it.LocaliteID == id }!!

    override suspend fun findLocalitesOfArrondissement(id: Int) =
        loadAllLocalites().filter { it.ArrondissementID == id }

    override suspend fun loadAllArrondissements() = listOf(
        Arrondissement(0, "NGAOUNDAL", 0),
        Arrondissement(1, "MAYO-BALEO", 1),

        Arrondissement(2, "LEMBE-YEZOUM", 2),
        Arrondissement(3, "EBEBDA", 3),

        Arrondissement(4, "GARI-GOMBO", 4),
        Arrondissement(5, "ABONG-MBANG", 5),

        Arrondissement(6, "BOGO", 6),
        Arrondissement(7, "DATCHEKA", 7),

        Arrondissement(8, "DIBOMBARI", 8),
        Arrondissement(9, "NKONDJOCK", 9)
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByArrondissementID(id: Int) =
        loadAllArrondissements().find { it.ArrondissementID == id }!!

    override suspend fun findArrondissementsOfDepartement(id: Int) =
        loadAllArrondissements().filter { it.DepartementID == id }

    override suspend fun loadAllDepartements() = listOf(
        Departement(0, "DJEREM", 0),
        Departement(1, "FARO ET DEO", 0),

        Departement(2, "HAUTE SANAGA", 1),
        Departement(3, "LEKIE", 1),

        Departement(4, "BOUMBA ET NGOKO", 2),
        Departement(5, "HAUT- NYONG", 2),

        Departement(6, "DIAMARE", 3),
        Departement(7, "MAYO DANAY", 3),

        Departement(8, "MOUNGO", 4),
        Departement(9, "NKAM", 4)
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByDepartementID(id: Int) =
        loadAllDepartements().find { it.DepartementID == id }!!

    override suspend fun findDepartementsOfRegion(id: Int) =
        loadAllDepartements().filter { it.RegionID == id }

    override suspend fun loadAllRegions() = listOf(
        Region(0, "ADAMAOUA"),
        Region(1, "CENTRE"),
        Region(2, "EST"),
        Region(3, "EXTREME-NORD"),
        Region(4, "LITTORAL")
    ).apply { Log.d(this[0]::class.java.name, Gson().toJson(this)) }

    override suspend fun findByRegionID(id: Int) =
        loadAllRegions().find { it.RegionID == id }!!

    override suspend fun findRegionOfLocality(id: Int) =
        findByRegionID(
            findByDepartementID(
                findByArrondissementID(
                    findByLocaliteID(id).ArrondissementID
                ).DepartementID
            ).RegionID
        )
}