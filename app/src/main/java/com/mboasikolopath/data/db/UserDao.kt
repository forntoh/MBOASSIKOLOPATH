package com.mboasikolopath.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mboasikolopath.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM User WHERE UserID LIKE 1 LIMIT 1")
    suspend fun loadUserAsync(): User

    @Query("SELECT * FROM User WHERE UserID LIKE 1 LIMIT 1")
    fun loadUser(): LiveData<User>

    @Query("DELETE FROM User")
    fun clear()
}