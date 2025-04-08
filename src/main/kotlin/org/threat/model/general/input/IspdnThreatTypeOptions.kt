package org.threat.model.general.input

enum class IspdnThreatTypeOptions(private val threatType: Int) {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    NONE(0);

    override fun toString() = threatType.toString()
}