package org.threat.model

import org.threat.model.offenders.Offenders
import org.threat.model.report.GeneralInformation
import org.threat.model.report.InfluenceObject
import org.threat.model.report.NetworkTableEntity

class ThreatReport {
    var generalInformation: GeneralInformation = GeneralInformation()
    var networkTable: List<NetworkTableEntity> = emptyList()
    var influenceObjects: List<InfluenceObject> = emptyList()
    var violatorsInformation: List<Offenders> = emptyList()
}