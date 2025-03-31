package org.threat.model.threats

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ThreatToInfluenceId(
    var threatId: Long = 0,
    val influenceObjectId: Long = 0
) : Serializable