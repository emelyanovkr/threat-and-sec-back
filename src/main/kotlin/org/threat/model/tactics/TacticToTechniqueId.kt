package org.threat.model.tactics

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class TacticToTechniqueId(
    var tacticId: Long = 0,
    val techniqueId: Long = 0
) : Serializable