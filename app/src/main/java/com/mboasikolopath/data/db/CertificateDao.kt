package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.Certificate

@Dao
interface CertificateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg certificates: Certificate)

    @Delete
    fun delete(vararg certificate: Certificate)

    @Query("SELECT * FROM Certificate")
    fun loadAll(): List<Certificate>

    @Query("SELECT * FROM Certificate WHERE CertificateID LIKE :id LIMIT 1")
    fun findByCertificateID(id: String): Certificate

    @Query("SELECT Certificate.* FROM Certificate LEFT OUTER JOIN Series ON Series.SeriesID = Certificate.SeriesID WHERE Certificate.SeriesID LIKE :seriesId AND Series.Cycle LIKE :cycle LIMIT 1")
    fun findCertificateForSeriesOfCycle(seriesId: String, cycle: Int): Certificate?
}