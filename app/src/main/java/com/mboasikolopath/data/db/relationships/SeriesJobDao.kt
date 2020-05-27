package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.SeriesJob
import com.mboasikolopath.data.model.relationships.pairs.SeriesJobPair

@Dao
interface SeriesJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg SeriesJobs: SeriesJob)

    @Query("SELECT * FROM SeriesJobPair")
    fun findSeriesAndJobPairs(): List<SeriesJobPair>

    @Query(
        """
        SELECT *
        FROM SeriesJobPair as s
        WHERE s.Series_SeriesID = :id
         """
    )
    fun findJobsBySeriesID(id: String): List<Job>

    @Query(
        """
        SELECT s.Series_SeriesID as SeriesID, s.Series_Name as Name, s.Series_Cycle as Cycle, s.Series_SpecialityID as SpecialityID
        FROM  SeriesJobPair as s
        WHERE s.JobID = :id
        """
    )
    fun findSeriesByJobID(id: Int): List<Series>
}