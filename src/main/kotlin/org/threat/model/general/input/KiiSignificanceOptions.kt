package org.threat.model.general.input

enum class KiiSignificanceOptions(private val significance: String) {
    SOCIAL("социальная значимость"),
    POLITICAL("политическая значимость"),
    ECONOMIC("экономическая значимость"),
    ECOLOGICAL("экологическая значимость"),
    DEFENSE("значимость для обеспечения обороны страны, безопасности государства и правопорядка"),
    NONE("NOT_AVAILABLE");

    override fun toString() = significance
}