package com.mboasikolopath.data.model.relationships

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.mboasikolopath.data.model.Section
import com.mboasikolopath.data.model.Speciality

@Entity(
    primaryKeys = ["SectionID", "SpecialityID"], foreignKeys = [ForeignKey(
        entity = Section::class,
        parentColumns = ["SectionID"],
        childColumns = ["SectionID"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Speciality::class,
        parentColumns = ["SpecialityID"],
        childColumns = ["SpecialityID"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["SectionID", "SpecialityID"], unique = true)]
)
data class SectionSpeciality(
    @SerializedName("sectionid") var SectionID: Int,
    @SerializedName("specialityid") var SpecialityID: Int
) {
    companion object {
        fun groupSpecialities(SectionSpecialityList: List<SectionSpecialityPair>): MutableList<SectionAndItsSpecialities> {
            return mutableListOf<SectionAndItsSpecialities>().also { items ->
                SectionSpecialityList
                    .groupBy(keySelector = { it.Section }, valueTransform = { it.Speciality })
                    .forEach { items.add(SectionAndItsSpecialities(it.key, it.value)) }
            }
        }
    }
}

class SectionSpecialityPair {
    @Embedded(prefix = "Section_")
    lateinit var Section: Section

    @Embedded
    lateinit var Speciality: Speciality
}

data class SectionAndItsSpecialities(
    val Section: Section?,
    val Specialities: List<Speciality>
)