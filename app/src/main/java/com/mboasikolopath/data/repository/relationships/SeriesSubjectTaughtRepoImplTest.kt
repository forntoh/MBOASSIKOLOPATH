package com.mboasikolopath.data.repository.relationships

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.SubjectTaught
import com.mboasikolopath.data.model.relationships.SeriesSubjectTaught
import com.mboasikolopath.data.model.relationships.SeriesSubjectTaughtPair
import com.mboasikolopath.data.repository.SeriesRepo
import com.mboasikolopath.data.repository.SubjectTaughtRepo

class SeriesSubjectTaughtRepoImplTest(
    val seriesRepo: SeriesRepo,
    val subjectTaughtRepo: SubjectTaughtRepo
) : SeriesSubjectTaughtRepo() {

    override suspend fun initData() = Unit

    override suspend fun findSeriesAndSubjectTaughtPairs(): List<SeriesSubjectTaughtPair> {
        val list = mutableListOf<SeriesSubjectTaughtPair>()
        for (i in 0..15) {
            for (j in 0..4) {
                list.add(SeriesSubjectTaughtPair().apply {
                    Series = seriesRepo.loadAll()[i]
                    SubjectTaught = subjectTaughtRepo.findBySubjectTaughtID(j)
                })
            }
        }
        return list.apply {
            Log.d(
                this[0]::class.java.name,
                Gson().toJson(this.map {
                    SeriesSubjectTaught(
                        it.Series.SeriesID,
                        it.SubjectTaught.SubjectTaughtID
                    )
                })
            )
        }
    }

    override suspend fun findSubjectsTaughtBySeriesID(id: String): List<SubjectTaught> {
        findSeriesAndSubjectTaughtPairs()
        return subjectTaughtRepo.loadAll()
    }
}