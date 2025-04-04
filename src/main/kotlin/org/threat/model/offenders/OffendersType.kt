package org.threat.model.offenders

enum class OffendersType(
    val placeholder: String,
    // for correct numbering generation
    val baseNumId: Long
) {
    EXCLUDED("\${violators_information_excluded}", 1),
    CHOSEN("\${violators_information_chosen}", 2),
    NONE("", 3)
}