package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.SeriesSchool
import com.mboasikolopath.data.model.relationships.pairs.SeriesSchoolPair

@Dao
interface SeriesSchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg SeriesSchools: SeriesSchool)

    @Query("SELECT * FROM SeriesSchoolPair")
    fun findSeriesAndSchoolPairs(): List<SeriesSchoolPair>

    @Query(
        """
        SELECT s.SchoolID, s.Name, s.LocaliteID 
        FROM SeriesSchoolPair as s
        WHERE s.Series_SeriesID = :id
        LIMIT 10
        """
    )
    fun findSchoolsBySeriesID(id: String): List<School>

    @Query(
        """
        SELECT s.Series_SeriesID as SeriesID, s.Series_Name as Name, s.Series_Cycle as Cycle, s.Series_SpecialityID as SpecialityID
        FROM SeriesSchoolPair as s
        WHERE s.SchoolID = :id
        LIMIT 20
        """
    )
    fun findSeriesBySchoolID(id: Int): List<Series>
}