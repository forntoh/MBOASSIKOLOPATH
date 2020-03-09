package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.SubjectTaught
import com.mboasikolopath.data.model.relationships.SeriesSubjectTaught
import com.mboasikolopath.data.model.relationships.SeriesSubjectTaughtPair

@Dao
interface SeriesSubjectTaughtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg SeriesSubjectTaught: SeriesSubjectTaught)

    @Query("SELECT Series.SeriesID as Series_SeriesID, Series.Name as Series_Name, Series.Cycle as Series_Cycle, Series.SpecialityID as Series_SpecialityID, SubjectTaught.* FROM Series LEFT OUTER JOIN SeriesSubjectTaught on SeriesSubjectTaught.SeriesID = Series.SeriesID INNER JOIN SubjectTaught on SeriesSubjectTaught.SubjectTaughtID = SubjectTaught.SubjectTaughtID")
    fun findSeriesAndSubjectTaughtPairs(): List<SeriesSubjectTaughtPair>

    @Query("SELECT SubjectTaught.* FROM Series LEFT OUTER JOIN SeriesSubjectTaught on SeriesSubjectTaught.SeriesID = Series.SeriesID LEFT OUTER JOIN SubjectTaught on SeriesSubjectTaught.SubjectTaughtID = SubjectTaught.SubjectTaughtID WHERE SeriesSubjectTaught.SeriesID = :id")
    fun findSubjectsTaughtBySeriesID(id: String): List<SubjectTaught>
}

