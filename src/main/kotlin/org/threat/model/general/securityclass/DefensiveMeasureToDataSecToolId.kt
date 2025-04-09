package org.threat.model.general.securityclass

import java.io.Serializable

data class DefensiveMeasureToDataSecToolId(
    var measure: Long? = null,
    var securityTool: Long? = null
) : Serializable