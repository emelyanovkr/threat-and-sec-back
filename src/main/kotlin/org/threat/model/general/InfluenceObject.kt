package org.threat.model.general

data class InfluenceObject(
    var objectName: String = "",
    var applies: Boolean = false
) {
    fun toDisplayMap(): Map<String, String> {
        return mapOf(
            "objectName" to this.objectName,
            "applies" to if (this.applies) "применяется" else "не применяется"
        )
    }
}