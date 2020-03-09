package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.School

@Dao
interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg schools: School)

    @Delete
    fun delete(vararg school: School)

    @Query("SELECT * FROM School")
    fun loadAll(): List<School>

    @Query("SELECT * FROM School WHERE SchoolID LIKE :id LIMIT 1")
    fun findBySchoolID(id: Int): School

    @Query("SELECT * FROM School WHERE LocaliteID LIKE :id")
    fun findSchoolsForLocality(id: Int): List<School>

    @Query("SELECT * FROM School WHERE LOWER(Name) LIKE LOWER(:query)")
    fun searchSchoolByName(query: String): List<School>

    @Query("Select school.* FROM region LEFT OUTER JOIN departement on departement.RegionID = region.RegionID LEFT OUTER JOIN arrondissement on arrondissement.DepartementID = departement.DepartementID LEFT OUTER JOIN localite on localite.ArrondissementID = arrondissement.ArrondissementID LEFT OUTER JOIN school on school.LocaliteID = localite.LocaliteID WHERE LOWER(region.RegionID) IN (:filterValues) AND LOWER(school.Name) Like LOWER(:query)")
    fun searchSchoolInRegion(query: String, filterValues: List<Int>): List<School>

    @Query("Select school.* FROM departement LEFT OUTER JOIN arrondissement on arrondissement.DepartementID = departement.DepartementID LEFT OUTER JOIN localite on localite.ArrondissementID = arrondissement.ArrondissementID LEFT OUTER JOIN school on school.LocaliteID = localite.LocaliteID WHERE LOWER(departement.DepartementID) IN (:filterValues) AND LOWER(school.Name) Like LOWER(:query)")
    fun searchSchoolInDepartement(query: String, filterValues: List<Int>): List<School>
}