package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.DeboucheDao
import com.mboasikolopath.data.model.Debouche
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.internal.runOnIoThread
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class DeboucheRepoImpl(
    private val deboucheDao: DeboucheDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : DeboucheRepo() {

    override suspend fun initData() = initDebouchesData()

    init {
        appDataSource.downloadedDebouches.observeForever {
            it?.let { scope.launch { saveDebouches(it) } }
        }
    }

    private suspend fun saveDebouches(debouches: List<Debouche>) {
        deboucheDao.insertAll(*debouches.toTypedArray())
        appStorage.setLastSaved(DataKey.DEBOUCHES, ZonedDateTime.now())
    }

    private suspend fun initDebouchesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.DEBOUCHES))) {
            appDataSource.debouches()
        }
    }

    override suspend fun loadAll(): List<Debouche> = runOnIoThread {
        initDebouchesData()
        deboucheDao.loadAll()
    }

    override suspend fun findByDeboucheID(id: Int): Debouche = runOnIoThread {
        deboucheDao.findByDeboucheID(id)
    }
}