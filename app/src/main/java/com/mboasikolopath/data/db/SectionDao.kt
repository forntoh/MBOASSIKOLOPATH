package com.mboasikolopath.data.db

import androidx.room.*
import com.mboasikolopath.data.model.Section

@Dao
interface SectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg sections: Section)

    @Delete
    fun delete(vararg section: Section)

    @Query("SELECT * FROM Section")
    fun loadAll(): List<Section>

    @Query("SELECT * FROM Section WHERE SectionID LIKE :id LIMIT 1")
    fun findBySectionID(id: Int): Section

    @Query("SELECT * FROM Section WHERE EducationID LIKE :id")
    fun findSectionsOfEducation(id: Int): List<Section>
}