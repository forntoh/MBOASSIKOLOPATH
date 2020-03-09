package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.Speciality
import com.mboasikolopath.data.model.relationships.SectionSpeciality
import com.mboasikolopath.data.model.relationships.SectionSpecialityPair

@Dao
interface SectionSpecialityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg SectionSpecialities: SectionSpeciality)

    @Query("SELECT Section.SectionID as Section_SectionID, Section.Description as Section_Description, Section.EducationID as Section_EducationID, Speciality.* FROM Section LEFT OUTER JOIN SectionSpeciality on SectionSpeciality.SectionID = Section.SectionID INNER JOIN Speciality on SectionSpeciality.SpecialityID = Speciality.SpecialityID")
    fun findSectionAndSpecialityPairs(): List<SectionSpecialityPair>

    @Query("SELECT Speciality.* FROM Section LEFT OUTER JOIN SectionSpeciality on SectionSpeciality.SectionID = Section.SectionID LEFT OUTER JOIN Speciality on SectionSpeciality.SpecialityID = Speciality.SpecialityID WHERE SectionSpeciality.SectionID = :id")
    fun findSpecialitiesBySectionID(id: Int): List<Speciality>
}