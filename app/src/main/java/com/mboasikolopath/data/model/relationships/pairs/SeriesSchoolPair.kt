package com.mboasikolopath.data.model.relationships.pairs

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mboasikolopath.data.model.School
import com.mboasikolopath.data.model.Series

@DatabaseView(
    """
    SELECT Series.SeriesID as Series_SeriesID, Series.Name as Series_Name, Series.Cycle as Series_Cycle, Series.SpecialityID as Series_SpecialityID, School.* 
    FROM Series
    LEFT OUTER JOIN SeriesSchool on SeriesSchool.SeriesID = Series.SeriesID 
    INNER JOIN School on SeriesSchool.SchoolID = School.SchoolID
    """
)
class SeriesSchoolPair {

    @Embedded(prefix = "Series_")
    lateinit var Series: Series

    @Embedded
    lateinit var School: School
}