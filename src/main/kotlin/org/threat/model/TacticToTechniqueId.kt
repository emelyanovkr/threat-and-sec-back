package org.threat.model

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class TacticToTechniqueId(
    var tacticId: Long = 0,
    val techniqueId: Long = 0
) : Serializable