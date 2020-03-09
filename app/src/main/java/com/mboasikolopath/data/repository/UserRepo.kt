package com.mboasikolopath.data.repository

import androidx.lifecycle.LiveData
import com.mboasikolopath.data.model.User

abstract class UserRepo: BaseRepository() {

    abstract suspend fun login(user: User)

    abstract suspend fun signup(user: User)

    abstract fun logout()

    abstract suspend fun getUser(): LiveData<User>

    abstract fun getUserAsync(): User?
}