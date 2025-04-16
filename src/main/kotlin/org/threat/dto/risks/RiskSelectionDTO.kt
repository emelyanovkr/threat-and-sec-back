package org.threat.dto.risks

data class RiskSelectionDTO(var riskId: Long = 0, var consequenceIds: List<Long> = emptyList())