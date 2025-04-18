package org.threat.model

import org.threat.model.general.GeneralInformation
import org.threat.model.general.InfluenceObject
import org.threat.model.general.NetworkTableEntity
import org.threat.model.offenders.Offenders
import org.threat.model.risks.RiskAndConsequenceDisplay
import org.threat.model.threats.ThreatInfo
import org.threat.model.threats.ThreatsExecutionMethod

class ThreatReport {
    var generalInformation: GeneralInformation = GeneralInformation()
    var networkTable: List<NetworkTableEntity> = emptyList()
    var risksAndConsequencesDisplay: List<RiskAndConsequenceDisplay> = emptyList()
    var influenceObjects: List<InfluenceObject> = emptyList()
    var violatorsInformationExcluded: List<Offenders> = emptyList()
    var violatorsInformationChosen: List<Offenders> = emptyList()
    var threatsExecutionMethods: List<ThreatsExecutionMethod> = emptyList()
    var actualChosenThreats: List<ThreatInfo> = emptyList()
}