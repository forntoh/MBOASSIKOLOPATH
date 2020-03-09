package com.mboasikolopath.data.repository

import kotlinx.coroutines.CoroutineScope

abstract class BaseRepository {
    lateinit var scope: CoroutineScope

    abstract suspend fun initData()
}