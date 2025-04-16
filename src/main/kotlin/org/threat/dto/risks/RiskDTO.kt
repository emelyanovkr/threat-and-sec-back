package org.threat.dto.risks

data class RiskDTO(val id: Long, val riskCode: String, val riskName: String, val consequences: Set<ConsequenceDTO>)