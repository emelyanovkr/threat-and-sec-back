package org.threat.dto

import org.threat.dto.risks.RiskSelectionDTO
import org.threat.model.general.GeneralInformation
import org.threat.model.general.InfluenceObject
import org.threat.model.general.NetworkTableEntity
import org.threat.model.offenders.Offenders
import org.threat.model.threats.ThreatInfo
import org.threat.model.threats.ThreatsExecutionMethod

class ThreatReportDTO {
    var generalInformation: GeneralInformation = GeneralInformation()
    var networkTable: List<NetworkTableEntity> = emptyList()
    var risksAndConsequences: List<RiskSelectionDTO> = emptyList()
    var influenceObjects: List<InfluenceObject> = emptyList()
    var violatorsInformationChosen: List<Offenders> = emptyList()
    var threatsExecutionMethods: List<ThreatsExecutionMethod> = emptyList()
    var actualChosenThreats: List<ThreatInfo> = emptyList()
}