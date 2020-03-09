package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Speciality

abstract class SpecialityRepo: BaseRepository() {

    abstract suspend fun loadAll(): List<Speciality>

    abstract suspend fun findBySpecialityID(id: Int): Speciality
}