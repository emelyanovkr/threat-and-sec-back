package org.threat.model.general.securityclass

import java.io.Serializable

data class DefensiveMeasureToSecurityClassId(
    var measure: Long? = null,
    var securityClass: Int? = null
) : Serializable