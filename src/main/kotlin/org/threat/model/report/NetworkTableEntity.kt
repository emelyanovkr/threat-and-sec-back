package org.threat.model.report

data class NetworkTableEntity(
    var systemName: String = "",
    var systemPurpose: String = "",
    var userCategories: String = "",
    var systemPriority: SystemPriority? = null,
    var userCount: Int = 0,
    var systemPosition: String = ""
) {
    enum class SystemPriority(private val priorityName: String) {
        HIGH("высокая"),
        MEDIUM("средняя"),
        MINIMAL("минимальная");

        override fun toString() = priorityName
    }
}
