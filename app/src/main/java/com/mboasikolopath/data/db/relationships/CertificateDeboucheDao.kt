package com.mboasikolopath.data.db.relationships

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.relationships.CertificateDebouche
import com.mboasikolopath.data.model.relationships.CertificateDebouchePair

@Dao
interface CertificateDeboucheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg CertificateDebouches: CertificateDebouche)

    @Query("SELECT Certificate.CertificateID as Certificate_CertificateID, Certificate.Name as Certificate_Name, Certificate.SeriesID as Certificate_SeriesID, Debouche.* FROM Certificate LEFT OUTER JOIN CertificateDebouche on CertificateDebouche.CertificateID = Certificate.CertificateID INNER JOIN Debouche on CertificateDebouche.DeboucheID = Debouche.DeboucheID")
    fun findCertificateAndDebouchePairs(): List<CertificateDebouchePair>

    @Query("SELECT Debouche.* FROM Certificate LEFT OUTER JOIN CertificateDebouche on CertificateDebouche.CertificateID = Certificate.CertificateID LEFT OUTER JOIN Debouche on CertificateDebouche.DeboucheID = Debouche.DeboucheID WHERE CertificateDebouche.CertificateID = :id")
    fun findDebouchesByCertificateID(id: String): List<Debouche>
}