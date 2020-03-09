package com.mboasikolopath.ui.main.home.explore.sector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mboasikolopath.data.repository.EducationRepo
import com.mboasikolopath.data.repository.SectionRepo
import com.mboasikolopath.data.repository.SeriesRepo
import com.mboasikolopath.data.repository.relationships.SectionSpecialityRepo
import com.mboasikolopath.internal.lazyDeferred
import com.mboasikolopath.internal.view.GenericListItem

class SectorViewModel(
    private val educationRepo: EducationRepo,
    private val sectionRepo: SectionRepo,
    private val sectionSpecialityRepo: SectionSpecialityRepo,
    private val seriesRepo: SeriesRepo
) : ViewModel() {

    init {
        educationRepo.scope = viewModelScope
        sectionRepo.scope = viewModelScope
        sectionSpecialityRepo.scope = viewModelScope
        seriesRepo.scope = viewModelScope
    }

    var education: Int? = null
    var sector: Int? = null

    val sectors by lazyDeferred {
        educationRepo.loadAll().map { e ->
            GenericListItem(
                e.EducationID.toString(),
                e.Description,
                sectionRepo.findSectionsOfEducation(e.EducationID).joinToString { it.Description }
            )
        }
    }

    val sectorOptions by lazyDeferred {
        education?.let { e ->
            sectionRepo.findSectionsOfEducation(e).map { section ->
                GenericListItem(
                    section.SectionID.toString(),
                    section.Description,
                    sectionSpecialityRepo.findSpecialitiesBySectionID(section.SectionID).joinToString { it.Description }
                )
            }
        }
    }

    val optionSeries by lazyDeferred {
        sector?.let { s ->
            sectionSpecialityRepo.findSpecialitiesBySectionID(s).map { speciality ->
                GenericListItem(
                    speciality.SpecialityID.toString(),
                    speciality.Description, "",
                    seriesRepo.findSeriesOfSpeciality(speciality.SpecialityID).filter { it.Cycle == 1 }.map { it.SeriesID },
                    seriesRepo.findSeriesOfSpeciality(speciality.SpecialityID).filter { it.Cycle == 2 }.map { it.SeriesID }
                )
            }
        }
    }
}
