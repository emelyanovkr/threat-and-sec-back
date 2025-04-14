package org.threat.model.general.input

enum class IspdnOwnWorkerOptions(private val worker: String) {
    YES("да"),
    NO("нет"),
    NONE("NOT_AVAILABLE");

    override fun toString() = worker
}