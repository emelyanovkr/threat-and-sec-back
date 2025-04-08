package org.threat.model.general.input

enum class IspdnOwnWorkerOptions(private val worker: String) {
    YES("да"),
    NO("нет"),
    NO_MATTER("не важно"),
    NONE("NOT_AVAILABLE");

    override fun toString() = worker
}