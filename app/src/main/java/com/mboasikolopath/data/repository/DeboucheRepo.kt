package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Debouche

abstract class DeboucheRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<Debouche>

    abstract suspend fun findByDeboucheID(id: Int): Debouche
}