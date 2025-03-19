package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import org.threat.model.ThreatInfo

@ApplicationScoped
class RelevantThreatsService {
    fun getRelevantThreats(influenceObjects: List<String>): List<ThreatInfo> {
        return ThreatInfo.findByInfluenceObjects(influenceObjects)
    }
}