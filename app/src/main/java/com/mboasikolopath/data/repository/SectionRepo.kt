package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.Section

abstract class SectionRepo: BaseRepository(){

    abstract suspend fun loadAll(): List<Section>

    abstract suspend fun findBySectionID(id: Int): Section

    abstract suspend fun findSectionsOfEducation(id: Int): List<Section>
}