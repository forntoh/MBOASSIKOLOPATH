package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.relationships.CertificateDebouche
import com.mboasikolopath.data.model.relationships.pairs.CertificateDebouchePair

@Dao
interface CertificateDeboucheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg CertificateDebouches: CertificateDebouche)

    @Query("SELECT * FROM CertificateDebouchePair")
    fun findCertificateAndDebouchePairs(): List<CertificateDebouchePair>

    @Query(
        """
        SELECT s.DeboucheID, s.Name
        FROM CertificateDebouchePair as s
        WHERE s.Certificate_CertificateID = :id
        """
    )
    fun findDebouchesByCertificateID(id: String): List<Debouche>
}