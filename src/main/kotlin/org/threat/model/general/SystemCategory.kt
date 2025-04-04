package org.threat.model.general

enum class SystemCategory(private val systemName: String) {
    GIS("ГИС"),
    ISPDN("ИСПДн"),
    KII("КИИ"),
    NONE("NOT_AVAILABLE");

    override fun toString() = systemName
}