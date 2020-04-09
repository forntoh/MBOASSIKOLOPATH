package com.mboasikolopath.ui.main.profile

import android.annotation.SuppressLint
import android.content.res.TypedArray
import androidx.lifecycle.ViewModel
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.UserRepo
import com.mboasikolopath.internal.view.SimpleListItem

class ProfileViewModel(
    private val locationRepo: LocationRepo,
    private var userRepo: UserRepo
) : ViewModel() {

    private fun getUserAsync() = userRepo.getUserAsync()

    @SuppressLint("ResourceType")
    suspend fun getProfileData(icons: TypedArray): List<SimpleListItem> {

        val user = getUserAsync() ?: return emptyList()

        val gender = if (user.Gender == "M") "Male" else "Female"
        val dob = user.Dob ?: ""

        val localite = locationRepo.findByLocaliteID(user.LocaliteID) ?: return emptyList()
        val arrondissement = locationRepo.findByArrondissementID(localite.ArrondissementID)
        val departement = locationRepo.findByArrondissementID(arrondissement.ArrondissementID)
        val region = locationRepo.findByDepartementID(departement.DepartementID)

        val location = "${region.Name} / ${departement.Name} / ${arrondissement.Name} / ${localite.Name}"

        return listOf(
            SimpleListItem(0, gender, 0, icons.getDrawable(0)),
            SimpleListItem(0, dob, 0, icons.getDrawable(1)),
            SimpleListItem(0, location, 0, icons.getDrawable(2)),
            SimpleListItem(0, "GCE O/Level", 0, icons.getDrawable(3)),
            SimpleListItem(0, "Dernière classe fréquentée - F3", 0, icons.getDrawable(4))
        )
    }
}
