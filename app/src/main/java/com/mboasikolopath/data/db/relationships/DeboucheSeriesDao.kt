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
        SELECT s.SeriesID as SeriesID, s.Name as Name, s.Cycle as Cycle, s.SpecialityID as SpecialityID
        FROM DeboucheSeriesPair as s
        WHERE s.Debouche_DeboucheID = :id
        """
    )
    fun findSeriessByDeboucheID(id: Int): List<Series>

    @Query(
        """
        SELECT s.Debouche_DeboucheID, s.Debouche_Name
        FROM DeboucheSeriesPair as s
        WHERE s.SeriesID = :id
        """
    )
    fun findDebouchesBySeriesID(id: Int): List<Debouche>
}