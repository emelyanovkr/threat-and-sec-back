package org.threat.model.general.input

enum class GisSignificanceOptions(private val significance: String) {
    FIRST("первый"),
    SECOND("второй"),
    THIRD("третий"),
    NONE("NOT_AVAILABLE");

    override fun toString() = significance
}