package com.mboasikolopath.data.model.relationships.pairs

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mboasikolopath.data.model.Section
import com.mboasikolopath.data.model.Speciality

@DatabaseView(
    """
    SELECT Section.SectionID as Section_SectionID, Section.Description as Section_Description, Section.LongDescription as Section_LongDescription, Section.EducationID as Section_EducationID, Speciality.* 
    FROM Section 
    LEFT OUTER JOIN SectionSpeciality on SectionSpeciality.SectionID = Section.SectionID 
    INNER JOIN Speciality on SectionSpeciality.SpecialityID = Speciality.SpecialityID
    """
)
class SectionSpecialityPair {

    @Embedded(prefix = "Section_")
    lateinit var Section: Section

    @Embedded
    lateinit var Speciality: Speciality
}