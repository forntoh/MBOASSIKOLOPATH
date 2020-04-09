package com.mboasikolopath.data.repository.relationships

import android.util.Log
import com.google.gson.Gson
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.model.relationships.CertificateDebouche
import com.mboasikolopath.data.model.relationships.pairs.CertificateDebouchePair
import com.mboasikolopath.data.repository.CertificateRepo
import com.mboasikolopath.data.repository.DeboucheRepo

class CertificateDeboucheRepoImplTest(val certificateRepo: CertificateRepo, val deboucheRepo: DeboucheRepo) : CertificateDeboucheRepo() {

    override suspend fun initData() = Unit

    override suspend fun findCertificateAndDebouchePairs(): List<CertificateDebouchePair> {
        val list = mutableListOf<CertificateDebouchePair>()
        for (i in 0..5) {
            for (j in 0..5) {
                list.add(
                    CertificateDebouchePair()
                        .apply {
                        Certificate = certificateRepo.loadAll()[i]
                        Debouche = deboucheRepo.findByDeboucheID(j)
                    }
                )
            }
        }
        return list.apply { Log.d(this[0]::class.java.name, Gson().toJson(this.map { CertificateDebouche(it.Certificate.CertificateID, it.Debouche.DeboucheID) })) }
    }

    override suspend fun findDebouchesByCertificateID(id: String): List<Debouche> {
        findCertificateAndDebouchePairs()
        return deboucheRepo.loadAll()
    }
}