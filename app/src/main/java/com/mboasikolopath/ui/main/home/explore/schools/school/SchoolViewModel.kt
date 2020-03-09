package com.mboasikolopath.ui.main.home.explore.schools.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SchoolRepo
import com.mboasikolopath.data.repository.relationships.SeriesSchoolRepo
import com.mboasikolopath.internal.lazyDeferred
import com.mboasikolopath.internal.view.CarouselItem
import com.mboasikolopath.internal.view.LocationDataItem

class SchoolViewModel(private val schoolId: Int, private val locationRepo: LocationRepo, private val schoolRepo: SchoolRepo, private val seriesSchoolRepo: SeriesSchoolRepo) : ViewModel() {

    init {
        locationRepo.scope = viewModelScope
        schoolRepo.scope = viewModelScope
        seriesSchoolRepo.scope = viewModelScope
    }

    val school by lazyDeferred {
        schoolRepo.findBySchoolID(schoolId)
    }

    val seriesFirstCycle by lazyDeferred {
        seriesSchoolRepo.findSeriesBySchoolID(schoolId).filter { it.Cycle == 1 }.map { it.SeriesID }
    }

    val seriesSecondCycle by lazyDeferred {
        seriesSchoolRepo.findSeriesBySchoolID(schoolId).filter { it.Cycle == 2 }.map { it.SeriesID }
    }

    val locationData by lazyDeferred {
        val school = schoolRepo.findBySchoolID(schoolId)
        val localite = locationRepo.findByLocaliteID(school.LocaliteID)
        val arrondissement = locationRepo.findByArrondissementID(localite.ArrondissementID)
        val departement = locationRepo.findByDepartementID(arrondissement.DepartementID)
        val region = locationRepo.findByRegionID(departement.RegionID)

        listOf(
            LocationDataItem("Region", region.Name),
            LocationDataItem("Departement", departement.Name),
            LocationDataItem("Arrondissement", arrondissement.Name),
            LocationDataItem("Localite", localite.Name)
        )
    }

    // TODO: Get photos from DB
    fun getPhotos() = listOf(
        CarouselItem("https://images.pexels.com/photos/267885/pexels-photo-267885.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
        CarouselItem("https://images.pexels.com/photos/289740/pexels-photo-289740.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
        CarouselItem("https://images.pexels.com/photos/265076/pexels-photo-265076.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
        CarouselItem("https://images.pexels.com/photos/21696/pexels-photo.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
        CarouselItem("https://images.pexels.com/photos/887584/pexels-photo-887584.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
        CarouselItem("https://images.pexels.com/photos/7096/people-woman-coffee-meeting.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500")
    )
}
