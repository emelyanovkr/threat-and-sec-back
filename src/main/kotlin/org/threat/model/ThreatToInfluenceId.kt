package org.threat.model

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class ThreatToInfluenceId(
    var threatId: Long = 0,
    val influenceObjectId: Long = 0
) : Serializable