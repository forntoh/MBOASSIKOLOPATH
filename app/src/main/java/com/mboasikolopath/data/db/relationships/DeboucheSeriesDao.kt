package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Query
import com.mboasikolopath.data.db.BaseDao
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.Series
import com.mboasikolopath.data.model.relationships.DeboucheSeries
import com.mboasikolopath.data.model.relationships.pairs.DeboucheSeriesPair

@Dao
interface DeboucheSeriesDao : BaseDao<DeboucheSeries> {

    @Query("SELECT * FROM DeboucheSeriesPair")
    fun findDeboucheAndSeriesPairs(): List<DeboucheSeriesPair>

    @Query(
        """
        SELECT s.SeriesID, s.Name, s.Cycle, s.SpecialityID
        FROM DeboucheSeriesPair as s
        WHERE s.Debouche_DeboucheID = :id
        """
    )
    fun findSeriesByDeboucheID(id: Int): List<Series>

    @Query(
        """
        SELECT s.Debouche_DeboucheID as DeboucheID, s.Debouche_Name as Name
        FROM DeboucheSeriesPair as s
        WHERE s.SeriesID = :id
        AND s.Cycle = :cycle
        """
    )
    fun findDebouchesBySeriesID(id: String, cycle: Int): List<Debouche>
}