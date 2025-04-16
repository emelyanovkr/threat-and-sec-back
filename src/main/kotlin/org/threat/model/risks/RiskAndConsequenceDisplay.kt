package org.threat.model.risks

data class RiskAndConsequenceDisplay(
    val riskCode: String,
    val riskName: String,
    val consequenceTitle: String,
    val consequenceDescription: String
) {

    fun toDisplayMap(): Map<String, String> {
        return mapOf(
            "riskCode" to riskCode,
            "riskName" to riskName,
            "conseqTitle" to consequenceTitle,
            "conseqDescription" to consequenceDescription
        )
    }
}