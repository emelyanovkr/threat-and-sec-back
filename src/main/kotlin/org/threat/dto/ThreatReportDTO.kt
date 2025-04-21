package org.threat.dto

import org.threat.dto.risks.RiskSelectionDTO
import org.threat.model.general.GeneralInformation
import org.threat.model.general.InfluenceObject
import org.threat.model.general.NetworkTableEntity

class ThreatReportDTO {
    var generalInformation: GeneralInformation = GeneralInformation()
    var networkTable: List<NetworkTableEntity> = emptyList()
    var risksAndConsequences: List<RiskSelectionDTO> = emptyList()
    var influenceObjects: List<InfluenceObject> = emptyList()
    var violatorsInformationChosenIds: List<Long> = emptyList()
    var threatsExecutionMethodsIds: List<Long> = emptyList()
    var actualChosenThreatsIds: List<Long> = emptyList()
}