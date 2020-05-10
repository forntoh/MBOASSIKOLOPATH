package com.mboasikolopath.data.repository.relationships

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.relationships.DeboucheSeries
import com.mboasikolopath.data.model.relationships.pairs.DeboucheSeriesPair
import com.mboasikolopath.data.repository.DeboucheRepo
import com.mboasikolopath.data.repository.SeriesRepo

class DeboucheSeriesRepoImplTest(val seriesRepo: SeriesRepo, private val deboucheRepo: DeboucheRepo) : DeboucheSeriesRepo() {

    override suspend fun initData() = Unit

    override suspend fun findDeboucheAndSeriesPairs(): List<DeboucheSeriesPair> {
        val list = mutableListOf<DeboucheSeriesPair>()
        for (i in 0..5) {
            for (j in 0..5) {
                list.add(
                    DeboucheSeriesPair()
                        .apply {
                        Series = seriesRepo.loadAll()[i]
                        Debouche = deboucheRepo.findByDeboucheID(j)
                    }
                )
            }
        }
        return list.apply { Log.d(this[0]::class.java.name, Gson().toJson(this.map { DeboucheSeries(it.Debouche.DeboucheID, it.Series.SeriesID) })) }
    }

    override suspend fun findDebouchesBySeriesID(id: String, cycle: Int): List<Debouche> {
        findDeboucheAndSeriesPairs()
        return deboucheRepo.loadAll()
    }
}