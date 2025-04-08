package org.threat.model.general.input

enum class IspdnSubjectCountOptions(private val count: String) {
    MORE100("более 100"),
    LESS100("менее 100"),
    ANY("любое"),
    NONE("NOT_AVAILABLE");

    override fun toString() = count
}