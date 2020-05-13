package com.mboasikolopath.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.model.School

@Dao
interface SchoolDao: BaseDao<School> {

    @Query("SELECT * FROM School LIMIT 125")
    fun loadAllPaged(): DataSource.Factory<Int, School>

    @Query("SELECT * FROM School WHERE SchoolID LIKE :id LIMIT 1")
    fun findBySchoolID(id: Int): School

    @Query("SELECT * FROM School WHERE LocaliteID LIKE :id LIMIT 20")
    fun findSchoolsForLocality(id: Int): List<School>

    @Query("SELECT * FROM School WHERE LOWER(Name) LIKE LOWER(:query) LIMIT 50")
    fun searchSchoolByName(query: String): DataSource.Factory<Int, School>

    @Query("Select school.* FROM region LEFT OUTER JOIN departement on departement.RegionID = region.RegionID LEFT OUTER JOIN arrondissement on arrondissement.DepartementID = departement.DepartementID LEFT OUTER JOIN localite on localite.ArrondissementID = arrondissement.ArrondissementID LEFT OUTER JOIN school on school.LocaliteID = localite.LocaliteID WHERE LOWER(region.RegionID) IN (:filterValues) AND LOWER(school.Name) Like LOWER(:query)")
    fun searchSchoolInRegion(query: String, filterValues: List<Int>): List<School>

    @Query("Select school.* FROM departement LEFT OUTER JOIN arrondissement on arrondissement.DepartementID = departement.DepartementID LEFT OUTER JOIN localite on localite.ArrondissementID = arrondissement.ArrondissementID LEFT OUTER JOIN school on school.LocaliteID = localite.LocaliteID WHERE LOWER(departement.DepartementID) IN (:filterValues) AND LOWER(school.Name) Like LOWER(:query)")
    fun searchSchoolInDepartement(query: String, filterValues: List<Int>): List<School>
}