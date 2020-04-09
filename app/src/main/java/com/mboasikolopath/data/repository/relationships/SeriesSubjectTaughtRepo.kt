package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.SubjectTaught
import com.mboasikolopath.data.model.relationships.pairs.SeriesSubjectTaughtPair
import com.mboasikolopath.data.repository.BaseRepository

abstract class SeriesSubjectTaughtRepo: BaseRepository() {

    abstract suspend fun findSeriesAndSubjectTaughtPairs(): List<SeriesSubjectTaughtPair>

    abstract suspend fun findSubjectsTaughtBySeriesID(id: String): List<SubjectTaught>
}

