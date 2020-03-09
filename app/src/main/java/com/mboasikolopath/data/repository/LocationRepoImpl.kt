package com.mboasikolopath.data.repository

import android.util.Log
import com.mboasikolopath.data.db.ArrondissementDao
import com.mboasikolopath.data.db.DepartementDao
import com.mboasikolopath.data.db.LocaliteDao
import com.mboasikolopath.data.db.RegionDao
import com.mboasikolopath.data.model.Arrondissement
import com.mboasikolopath.data.model.Departement
import com.mboasikolopath.data.model.Localite
import com.mboasikolopath.data.model.Region
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import com.mboasikolopath.data.pref.DataKey
import com.mboasikolopath.utilities.isFetchNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class LocationRepoImpl(
    private val regionDao: RegionDao,
    private var departementDao: DepartementDao,
    private val arrondissementDao: ArrondissementDao,
    private val localiteDao: LocaliteDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : LocationRepo() {

    override suspend fun initData() {
        initRegionsData()
        initDepartementsData()
        initArrondissementsData()
        initLocalitiesData()
    }

    private val regions = mutableListOf<Region>()
    private val departements = mutableListOf<Departement>()
    private val arrondissements = mutableListOf<Arrondissement>()
    private val localites = mutableListOf<Localite>()

    init {
        appDataSource.downloadedRegions.observeForever { r ->
            r?.let { regions.addAll(it) }
        }
        appDataSource.downloadedDepartements.observeForever { d ->
            d?.let { departements.addAll(it) }
        }
        appDataSource.downloadedArrondissements.observeForever { a ->
            a?.let { arrondissements.addAll(it) }
        }
        appDataSource.downloadedLocalities.observeForever { l ->
            l?.let { localites.addAll(it); scope.launch { saveLocationData() } }
        }
    }

    private suspend fun saveLocationData() {
        regionDao.insertAll(*regions.toTypedArray())
        Log.d("TOM", "Saved regions.........................!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        departementDao.insertAll(*departements.toTypedArray())
        Log.d("TOM", "Saved departements....................!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        arrondissementDao.insertAll(*arrondissements.toTypedArray())
        Log.d("TOM", "Saved arrondissements.................!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        localiteDao.insertAll(*localites.toTypedArray())
        Log.d("TOM", "Saved localites.......................!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    }

    private suspend fun initLocalitiesData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.LOCALITIES)) || localiteDao.numberOfItems() <= 0) {
            appDataSource.localities()
            appStorage.setLastSaved(DataKey.LOCALITIES, ZonedDateTime.now())
        }
    }

    private suspend fun initArrondissementsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.ARRONDISSEMENTS)) || arrondissementDao.numberOfItems() <= 0) {
            appDataSource.arrondissements()
            appStorage.setLastSaved(DataKey.ARRONDISSEMENTS, ZonedDateTime.now())
        }
    }

    private suspend fun initDepartementsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.DEPARTEMENTS)) || departementDao.numberOfItems() <= 0) {
            appDataSource.departements()
            appStorage.setLastSaved(DataKey.DEPARTEMENTS, ZonedDateTime.now())
        }
    }

    private suspend fun initRegionsData() {
        if (isFetchNeeded(appStorage.getLastSaved(DataKey.REGIONS)) || regionDao.numberOfItems() <= 0) {
            appDataSource.regions()
            appStorage.setLastSaved(DataKey.REGIONS, ZonedDateTime.now())
        }
    }

    override suspend fun loadAllLocalites() = withContext(Dispatchers.IO) {
        initLocalitiesData()
        return@withContext localiteDao.loadAll()
    }

    override suspend fun findByLocaliteID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext localiteDao.findByLocaliteID(id)
    }

    override suspend fun findLocalitesOfArrondissement(id: Int) = withContext(Dispatchers.IO) {
        return@withContext localiteDao.findLocalitesOfArrondissement(id)
    }

    override suspend fun loadAllArrondissements() = withContext(Dispatchers.IO) {
        initArrondissementsData()
        return@withContext arrondissementDao.loadAll()
    }

    override suspend fun findByArrondissementID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext arrondissementDao.findByArrondissementID(id)
    }

    override suspend fun findArrondissementsOfDepartement(id: Int) = withContext(Dispatchers.IO) {
        return@withContext arrondissementDao.findArrondissementsOfDepartement(id)
    }

    override suspend fun loadAllDepartements() = withContext(Dispatchers.IO) {
        initDepartementsData()
        return@withContext departementDao.loadAll()
    }

    override suspend fun findByDepartementID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext departementDao.findByDepartementID(id)
    }

    override suspend fun findDepartementsOfRegion(id: Int) = withContext(Dispatchers.IO) {
        return@withContext departementDao.findDepartementsOfRegion(id)
    }

    override suspend fun loadAllRegions() = withContext(Dispatchers.IO) {
        initRegionsData()
        return@withContext regionDao.loadAll()
    }

    override suspend fun findByRegionID(id: Int) = withContext(Dispatchers.IO) {
        return@withContext regionDao.findByRegionID(id)
    }

    override suspend fun findRegionOfLocality(id: Int) = withContext(Dispatchers.IO) {
        return@withContext findByRegionID(
            findByDepartementID(
                findByArrondissementID(
                    findByLocaliteID(id).ArrondissementID
                ).DepartementID
            ).RegionID
        )
    }
}