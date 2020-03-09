package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Arrondissement
import com.mboasikolopath.data.model.Departement
import com.mboasikolopath.data.model.Localite
import com.mboasikolopath.data.model.Region

abstract class LocationRepo: BaseRepository() {

    abstract suspend fun loadAllLocalites(): List<Localite>

    abstract suspend fun findByLocaliteID(id: Int): Localite

    abstract suspend fun findLocalitesOfArrondissement(id: Int): List<Localite>

    abstract suspend fun loadAllArrondissements(): List<Arrondissement>

    abstract suspend fun findByArrondissementID(id: Int): Arrondissement

    abstract suspend fun findArrondissementsOfDepartement(id: Int): List<Arrondissement>

    abstract suspend fun loadAllDepartements(): List<Departement>

    abstract suspend fun findByDepartementID(id: Int): Departement

    abstract suspend fun findDepartementsOfRegion(id: Int): List<Departement>

    abstract suspend fun loadAllRegions(): List<Region>

    abstract suspend fun findByRegionID(id: Int): Region

    abstract suspend fun findRegionOfLocality(id: Int): Region
}