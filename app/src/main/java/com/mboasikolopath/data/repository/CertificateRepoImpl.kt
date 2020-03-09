package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.CertificateDao
import com.mboasikolopath.data.model.Certificate
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class CertificateRepoImpl(
    private val certificateDao: CertificateDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : CertificateRepo() {

    override suspend fun initData() = initCertificatesData()

    init {
        appDataSource.downloadedCertificates.observeForever {
            it?.let { scope.launch { saveCertificates(it) } }
        }
    }

    private suspend fun saveCertificates(certificates: List<Certificate>) =
        certificateDao.insertAll(*certificates.toTypedArray())

    private suspend fun initCertificatesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.CERTIFICATES))) {
            appDataSource.certificates()
            appStorage.setLastSaved(DataKey.CERTIFICATES, ZonedDateTime.now())
        }
    }

    override suspend fun loadAll() = withContext(Dispatchers.IO) {
        initCertificatesData()
        return@withContext certificateDao.loadAll()
    }

    override suspend fun findByCertificateID(id: String) = withContext(Dispatchers.IO) {
        return@withContext certificateDao.findByCertificateID(id)
    }

    override suspend fun findCertificateForSeriesOfCycle(seriesId: String, cycle: Int) = withContext(Dispatchers.IO) {
        return@withContext certificateDao.findCertificateForSeriesOfCycle(seriesId, cycle)
    }
}