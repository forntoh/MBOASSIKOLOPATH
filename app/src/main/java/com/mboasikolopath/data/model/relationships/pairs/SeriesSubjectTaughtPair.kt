package com.mboasikolopath.data.model.relationships.pairs

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.SubjectTaught

@DatabaseView(
    """
    SELECT Series.SeriesID as Series_SeriesID, Series.Name as Series_Name, Series.Cycle as Series_Cycle, Series.SpecialityID as Series_SpecialityID, SubjectTaught.* 
    FROM Series 
    LEFT OUTER JOIN SeriesSubjectTaught on SeriesSubjectTaught.SeriesID = Series.SeriesID 
    INNER JOIN SubjectTaught on SeriesSubjectTaught.SubjectTaughtID = SubjectTaught.SubjectTaughtID
    """
)
class SeriesSubjectTaughtPair {

    @Embedded(prefix = "Series_")
    lateinit var Series: Series

    @Embedded
    lateinit var SubjectTaught: SubjectTaught
}