package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.relationships.CertificateDebouchePair
import com.mboasikolopath.data.repository.BaseRepository

abstract class CertificateDeboucheRepo: BaseRepository() {

    abstract suspend fun findCertificateAndDebouchePairs(): List<CertificateDebouchePair>

    abstract suspend fun findDebouchesByCertificateID(id: String): List<Debouche>
}