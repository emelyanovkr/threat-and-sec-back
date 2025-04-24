package org.threat.model.offenders

import java.io.Serializable

data class OffenderPowerLevelToOffenderId(
    var offender: Long? = null,
    var powerLevel: Long? = null
) : Serializable