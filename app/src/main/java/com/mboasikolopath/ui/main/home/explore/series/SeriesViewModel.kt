package com.mboasikolopath.ui.main.home.explore.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mboasikolopath.data.repository.CertificateRepo
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.SeriesRepo
import com.mboasikolopath.data.repository.relationships.DeboucheSeriesRepo
import com.mboasikolopath.data.repository.relationships.SeriesJobRepo
import com.mboasikolopath.data.repository.relationships.SeriesSchoolRepo
import com.mboasikolopath.data.repository.relationships.SeriesSubjectTaughtRepo
import com.mboasikolopath.internal.lazyDeferred
import com.mboasikolopath.internal.view.AccessConditionItem
import com.mboasikolopath.internal.view.GenericListItem
import com.mboasikolopath.internal.view.SimpleListItem

class SeriesViewModel(
    private val seriesId: String,
    private val cycle: Int,
    private val seriesRepo: SeriesRepo,
    private val certificateRepo: CertificateRepo,
    private val deboucheSeriesRepo: DeboucheSeriesRepo,
    private val seriesSubjectTaughtRepo: SeriesSubjectTaughtRepo,
    private val seriesJobRepo: SeriesJobRepo,
    private val seriesSchoolRepo: SeriesSchoolRepo,
    private val locationRepo: LocationRepo
) : ViewModel() {

    init {
        seriesRepo.scope = viewModelScope
        certificateRepo.scope = viewModelScope
        deboucheSeriesRepo.scope = viewModelScope
        seriesSubjectTaughtRepo.scope = viewModelScope
        seriesJobRepo.scope = viewModelScope
        seriesSchoolRepo.scope = viewModelScope
        locationRepo.scope = viewModelScope
    }

    val series by lazyDeferred {
        seriesRepo.findBySeriesID(seriesId)
    }

    val diploma by lazyDeferred {
        "${certificateRepo.findCertificateForSeriesOfCycle(
            seriesId,
            cycle
        )?.CertificateID}: ${seriesRepo.findBySeriesID(
            seriesId
        ).SeriesID}"
    }

    val subjectsTaught by lazyDeferred {
        seriesSubjectTaughtRepo.findSubjectsTaughtBySeriesID(seriesId).map {
            SimpleListItem(it.SubjectTaughtID, it.Name)
        }
    }

    val jobs by lazyDeferred {
        seriesJobRepo.findJobsBySeriesID(seriesId).map { it.Name }
    }

    val relatedSchools by lazyDeferred {
        seriesSchoolRepo.findSchoolsBySeriesID(seriesId).map {
            GenericListItem(
                it.SchoolID.toString(),
                it.Name,
                locationRepo.findRegionOfLocality(it.LocaliteID)?.Name
            )
        }
    }

    val debouches by lazyDeferred {
        deboucheSeriesRepo.findDebouchesBySeriesID(seriesId, cycle).map {
            it.Name
        }
    }

    // TODO: Get access conditions from DB
    fun getAccessConditions() = listOf(
        AccessConditionItem("CEP/FSLC", false),
        AccessConditionItem("Réussite au concours", false)
    )

    // TODO: Get acceder a from DB
    fun getAccederA() = "Accéder en 2nde Électrotechnique (F3) "

    // TODO: Get skills obtained from DB
    fun getSkillsObtained() = listOf(
        SimpleListItem(0, "Presentation generale du metier et de la form...", 1),
        SimpleListItem(0, "Qualite, Hygiene, Securite et Environnement", 1),
        SimpleListItem(0, "Entrepreneuriat", 1)
    )
}
