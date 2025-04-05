package org.threat.model.threats

enum class DataInsertionType(var placeholder: String) {
    THREATS_METHODS("\${threats_execution_methods}"),
    ACTUAL_THREATS("\${actual_excluded_threats_with_categories}")
}