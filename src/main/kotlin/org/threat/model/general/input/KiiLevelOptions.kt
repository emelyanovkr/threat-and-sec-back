package org.threat.model.general.input

enum class KiiLevelOptions(private val kiiLevel: String) {
    LEVEL1("уровень 1"),
    LEVEL2("уровень 2"),
    LEVEL3("уровень 3"),
    LEVEL4("уровень 4"),
    LEVEL5("уровень 5"),
    NONE("NOT_AVAILABLE");

    override fun toString() = kiiLevel
}