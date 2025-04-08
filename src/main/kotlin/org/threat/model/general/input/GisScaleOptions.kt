package org.threat.model.general.input

enum class GisScaleOptions(private val scale: String) {
    FEDERAL("федеральный"),
    REGIONAL("региональный"),
    OBJECT("объектовый"),
    NONE("NOT_AVAILABLE");

    override fun toString() = scale
}