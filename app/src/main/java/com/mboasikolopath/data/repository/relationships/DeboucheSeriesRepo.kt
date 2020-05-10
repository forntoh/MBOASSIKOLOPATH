package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.relationships.pairs.DeboucheSeriesPair
import com.mboasikolopath.data.repository.BaseRepository

abstract class DeboucheSeriesRepo: BaseRepository() {

    abstract suspend fun findDeboucheAndSeriesPairs(): List<DeboucheSeriesPair>

    abstract suspend fun findDebouchesBySeriesID(id: String, cycle: Int): List<Debouche>
}