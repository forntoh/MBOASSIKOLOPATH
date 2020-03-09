package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Series

abstract class SeriesRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<Series>

    abstract suspend fun findBySeriesID(id: String): Series

    abstract suspend fun findSeriesOfSpeciality(id: Int): List<Series>
}