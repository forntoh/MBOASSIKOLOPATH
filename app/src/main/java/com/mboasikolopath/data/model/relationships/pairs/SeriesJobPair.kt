package com.mboasikolopath.data.model.relationships.pairs

import androidx.room.Embedded
import com.mboasikolopath.data.model.Job
import com.mboasikolopath.data.model.Series

class SeriesJobPair {
    @Embedded(prefix = "Series_")
    lateinit var series: Series

    @Embedded
    lateinit var job: Job
}