package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Certificate

abstract class CertificateRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<Certificate>

    abstract suspend fun findByCertificateID(id: String): Certificate

    abstract suspend fun findCertificateForSeriesOfCycle(seriesId: String, cycle: Int): Certificate?
}