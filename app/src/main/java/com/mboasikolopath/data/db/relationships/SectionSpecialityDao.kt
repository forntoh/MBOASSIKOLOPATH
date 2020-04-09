package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.Speciality
import com.mboasikolopath.data.model.relationships.SectionSpeciality
import com.mboasikolopath.data.model.relationships.pairs.SectionSpecialityPair

@Dao
interface SectionSpecialityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg SectionSpecialities: SectionSpeciality)

    @Query("SELECT * FROM SectionSpecialityPair")
    fun findSectionAndSpecialityPairs(): List<SectionSpecialityPair>

    @Query(
        """
        SELECT s.SpecialityID, s.Description
        FROM SectionSpecialityPair as s
        WHERE s.Section_SectionID = :id
        """
    )
    fun findSpecialitiesBySectionID(id: Int): List<Speciality>
}