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

    @Query("SELECT Series.SeriesID as Series_SeriesID, Series.Name as Series_Name, Series.Cycle as Series_Cycle, Series.SpecialityID as Series_SpecialityID, Job.* FROM Series LEFT OUTER JOIN SeriesJob on SeriesJob.SeriesID = Series.SeriesID INNER JOIN Job on SeriesJob.JobID = Job.JobID")
    fun findSeriesAndJobPairs(): List<SeriesJobPair>

    @Query("SELECT Job.* FROM Series LEFT OUTER JOIN SeriesJob on SeriesJob.SeriesID = Series.SeriesID LEFT OUTER JOIN Job on SeriesJob.JobID = Job.JobID WHERE SeriesJob.SeriesID = :id")
    fun findJobsBySeriesID(id: String): List<Job>

    @Query("SELECT Series.* FROM Job LEFT OUTER JOIN SeriesJob on SeriesJob.SeriesID = Job.JobID LEFT OUTER JOIN Series on SeriesJob.SeriesID = Series.SeriesID WHERE SeriesJob.JobID = :id")
    fun findSeriesByJobID(id: Int): List<Series>
}