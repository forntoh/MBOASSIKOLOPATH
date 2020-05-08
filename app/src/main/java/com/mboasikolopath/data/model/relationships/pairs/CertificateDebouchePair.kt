package com.mboasikolopath.data.model.relationships.pairs

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mboasikolopath.data.model.Certificate
import com.mboasikolopath.data.model.Debouche

@DatabaseView(
    """
    SELECT Certificate.CertificateID as Certificate_CertificateID, Certificate.SeriesID as Certificate_SeriesID, Debouche.* 
    FROM Certificate 
    LEFT OUTER JOIN CertificateDebouche on CertificateDebouche.CertificateID = Certificate.CertificateID 
    INNER JOIN Debouche on CertificateDebouche.DeboucheID = Debouche.DeboucheID
    """
)
class CertificateDebouchePair {

    @Embedded(prefix = "Certificate_")
    lateinit var Certificate: Certificate

    @Embedded
    lateinit var Debouche: Debouche
}