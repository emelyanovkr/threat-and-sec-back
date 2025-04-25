package org.threat.dto

import org.threat.model.general.securityclass.BasicDefensiveMeasure

data class DefensiveMeasureDTO(var securityClass: Int, var basicDefensiveMeasures: List<BasicDefensiveMeasure>)