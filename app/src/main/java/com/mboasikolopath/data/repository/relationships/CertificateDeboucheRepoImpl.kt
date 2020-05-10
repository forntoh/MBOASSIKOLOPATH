package com.mboasikolopath.data.repository.relationships

import com.mboasikolopath.data.model.relationships.CertificateDebouche
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class CertificateDeboucheRepoImpl(
    private val certificateDeboucheDao: CertificateDeboucheDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : CertificateDeboucheRepo() {

    override suspend fun initData() = initCertificateDebouchesData()

    init {
        appDataSource.downloadedCertificateDebouches.observeForever {
            scope.launch { saveCertificateDebouches(it) }
        }
    }

    private suspend fun saveCertificateDebouches(certificateDebouches: List<CertificateDebouche>) =
        certificateDeboucheDao.insertAll(*certificateDebouches.toTypedArray())

    private suspend fun initCertificateDebouchesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.CERTIFICATE_DEBOUCHES))) {
            appDataSource.certificateDebouches()
            appStorage.setLastSaved(DataKey.CERTIFICATE_DEBOUCHES, ZonedDateTime.now())
        }
    }

    override suspend fun findCertificateAndDebouchePairs() = withContext(Dispatchers.IO) {
        initCertificateDebouchesData()
        return@withContext certificateDeboucheDao.findCertificateAndDebouchePairs()
    }

    override suspend fun findDebouchesByCertificateID(id: String) = withContext(Dispatchers.IO) {
        return@withContext certificateDeboucheDao.findDebouchesByCertificateID(id)
    }
}