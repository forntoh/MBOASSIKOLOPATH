package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.CertificateDao
import com.mboasikolopath.data.model.Certificate
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.internal.runOnIoThread
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
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

    private suspend fun saveCertificates(certificates: List<Certificate>) {
        certificateDao.insertAll(*certificates.toTypedArray())
        appStorage.setLastSaved(DataKey.CERTIFICATES, ZonedDateTime.now())
    }

    private suspend fun initCertificatesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.CERTIFICATES))) {
            appDataSource.certificates()
        }
    }

    override suspend fun loadAll() = runOnIoThread {
        initCertificatesData()
        certificateDao.loadAll()
    }

    override suspend fun findByCertificateID(id: String) = runOnIoThread {
        certificateDao.findByCertificateID(id)
    }

    override suspend fun findCertificateForSeriesOfCycle(seriesId: String, cycle: Int) = runOnIoThread {
        certificateDao.findCertificateForSeriesOfCycle(seriesId, cycle)
    }
}