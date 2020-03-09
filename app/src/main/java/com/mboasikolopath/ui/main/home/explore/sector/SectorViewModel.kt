package com.mboasikolopath.ui.main.home.explore.sector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mboasikolopath.data.repository.EducationRepo
import com.mboasikolopath.data.repository.SectionRepo
import com.mboasikolopath.data.repository.SeriesRepo
import com.mboasikolopath.data.repository.relationships.SectionSpecialityRepo
import com.mboasikolopath.internal.view.GenericListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun sectors() = withContext(Dispatchers.IO) {
        return@withContext educationRepo.loadAll().map { e ->
            GenericListItem(
                e.EducationID.toString(),
                e.Description,
                sectionRepo.findSectionsOfEducation(e.EducationID).joinToString { it.Description },
                e.LongDescription
            )
        }
    }

    suspend fun sectorOptions(education: Int) = withContext(Dispatchers.IO) {
        return@withContext sectionRepo.findSectionsOfEducation(education).map { section ->
            GenericListItem(
                section.SectionID.toString(),
                section.Description,
                sectionSpecialityRepo.findSpecialitiesBySectionID(section.SectionID).joinToString { it.Description },
                educationRepo.findByEducationID(education).LongDescription
            )
        }
    }

    suspend fun optionSeries(sector: Int) = withContext(Dispatchers.IO) {
        return@withContext sectionSpecialityRepo.findSpecialitiesBySectionID(sector).map { speciality ->
            GenericListItem(
                speciality.SpecialityID.toString(),
                speciality.Description, "",
                sectionRepo.findBySectionID(sector).LongDescription,
                seriesRepo.findSeriesOfSpecialityOfCycle(speciality.SpecialityID, 1).map { it.SeriesID },
                seriesRepo.findSeriesOfSpecialityOfCycle(speciality.SpecialityID, 2).map { it.SeriesID }
            )
        }
    }

    suspend fun optionSeries(sector: Int, cycle: Int) = withContext(Dispatchers.IO) {
        return@withContext sectionSpecialityRepo.findSpecialitiesBySectionID(sector).map { speciality ->
            GenericListItem(
                speciality.SpecialityID.toString(),
                speciality.Description, "",
                sectionRepo.findBySectionID(sector).LongDescription,
                if (cycle == 1) seriesRepo.findSeriesOfSpecialityOfCycle(speciality.SpecialityID, cycle).map { it.SeriesID } else emptyList(),
                if (cycle == 2) seriesRepo.findSeriesOfSpecialityOfCycle(speciality.SpecialityID, cycle).map { it.SeriesID } else emptyList()
            )
        }
    }
}
