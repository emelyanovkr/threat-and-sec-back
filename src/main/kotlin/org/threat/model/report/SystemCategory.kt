package org.threat.model.report

enum class SystemCategory(private val systemName: String) {
    GIS("ГИС"),
    ISPDN("ИСПДн"),
    KII("КИИ");

    override fun toString() = systemName
}