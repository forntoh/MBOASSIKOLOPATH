package com.mboasikolopath.data.model.relationships.pairs

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.Series

@DatabaseView(
    """
    SELECT Series.SeriesID as Series_SeriesID, Series.Name as Series_Name, Series.Cycle as Series_Cycle, Series.SpecialityID as Series_SpecialityID, Job.* 
    FROM Series 
    LEFT OUTER JOIN SeriesJob on SeriesJob.SeriesID = Series.SeriesID 
    INNER JOIN Job on SeriesJob.JobID = Job.JobID
    """
)
class SeriesJobPair {

    @Embedded(prefix = "Series_")
    lateinit var series: Series

    @Embedded
    lateinit var job: Job
}