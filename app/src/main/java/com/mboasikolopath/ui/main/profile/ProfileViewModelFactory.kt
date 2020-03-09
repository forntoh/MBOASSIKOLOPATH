package com.mboasikolopath.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mboasikolopath.data.repository.LocationRepo
import com.mboasikolopath.data.repository.UserRepo

class ProfileViewModelFactory(private val locationRepo: LocationRepo, private var userRepo: UserRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(locationRepo, userRepo) as T
    }
}