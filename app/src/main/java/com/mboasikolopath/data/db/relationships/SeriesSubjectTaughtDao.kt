package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.SubjectTaught
import com.mboasikolopath.data.model.relationships.SeriesSubjectTaught
import com.mboasikolopath.data.model.relationships.pairs.SeriesSubjectTaughtPair

@Dao
interface SeriesSubjectTaughtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg SeriesSubjectTaught: SeriesSubjectTaught)

    @Query("SELECT * FROM SeriesSubjectTaughtPair")
    fun findSeriesAndSubjectTaughtPairs(): List<SeriesSubjectTaughtPair>

    @Query(
        """
        SELECT s.SubjectTaughtID, s.Name 
        FROM SeriesSubjectTaughtPair as s
        WHERE s.Series_SeriesID = :id
        """
    )
    fun findSubjectsTaughtBySeriesID(id: String): List<SubjectTaught>
}

