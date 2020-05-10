package com.mboasikolopath.data.model.relationships.pairs

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.Series

@DatabaseView(
    """
    SELECT Debouche.DeboucheID as Debouche_DeboucheID, Debouche.Name as Debouche_Name, Series.*
    FROM Debouche
    LEFT OUTER JOIN DeboucheSeries ON DeboucheSeries.DeboucheID = Debouche.DeboucheID
    LEFT OUTER JOIN Series ON DeboucheSeries.SeriesID = Series.SeriesID
    """
)
class DeboucheSeriesPair {

    @Embedded(prefix = "Debouche_")
    lateinit var Debouche: Debouche

    @Embedded
    lateinit var Series: Series
}