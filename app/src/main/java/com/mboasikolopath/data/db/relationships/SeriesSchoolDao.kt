package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.SeriesSchool
import com.mboasikolopath.data.model.relationships.SeriesSchoolPair

@Dao
interface SeriesSchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg SeriesSchools: SeriesSchool)

    @Query("SELECT Series.SeriesID as Series_SeriesID, Series.Name as Series_Name, Series.Cycle as Series_Cycle, Series.SpecialityID as Series_SpecialityID, School.* FROM Series LEFT OUTER JOIN SeriesSchool on SeriesSchool.SeriesID = Series.SeriesID INNER JOIN School on SeriesSchool.SchoolID = School.SchoolID")
    fun findSeriesAndSchoolPairs(): List<SeriesSchoolPair>

    @Query("SELECT School.* FROM Series LEFT OUTER JOIN SeriesSchool on SeriesSchool.SeriesID = Series.SeriesID LEFT OUTER JOIN School on SeriesSchool.SchoolID = School.SchoolID WHERE SeriesSchool.SeriesID = :id")
    fun findSchoolsBySeriesID(id: String): List<School>

    @Query("SELECT Series.* FROM School, Series AS s LEFT OUTER JOIN SeriesSchool on SeriesSchool.SeriesID = s.SeriesID LEFT OUTER JOIN Series on SeriesSchool.SchoolID = s.SeriesID WHERE SeriesSchool.SchoolID = :id")
    fun findSeriesBySchoolID(id: Int): List<Series>
}