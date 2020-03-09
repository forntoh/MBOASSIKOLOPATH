package com.mboasikolopath.data.repository

import com.mboasikolopath.data.db.UserDao
import com.mboasikolopath.data.model.User
import com.mboasikolopath.data.network.AppDataSource
import com.mboasikolopath.data.pref.AppStorage
import kotlinx.coroutines.launch

class UserRepoImpl(
    private val userDao: UserDao,
    private val appDataSource: AppDataSource,
    private val appStorage: AppStorage
) : UserRepo() {

    override suspend fun initData() = Unit

    init {
        appDataSource.downloadedUser.observeForever {
            scope.launch { saveUser(it) }
        }
    }

    private suspend fun saveUser(user: User?) =
        user?.let {
            appStorage.saveUser(it.apply { UserID = 1; LocaliteID = 1 })
            userDao.saveUser(it.apply { UserID = 1; LocaliteID = 1 })
        }

    override suspend fun login(user: User) = appDataSource.login(user)

    override suspend fun signup(user: User) = appDataSource.signup(user)

    override fun logout() = userDao.clear()

    override suspend fun getUser() =  userDao.loadUser()

    override fun getUserAsync() =  appStorage.loadUser()
}