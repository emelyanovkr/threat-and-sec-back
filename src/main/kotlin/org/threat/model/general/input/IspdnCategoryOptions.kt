package org.threat.model.general.input

enum class IspdnCategoryOptions(private val category: String) {
    SPECIAL("специальные"),
    BIOMETRICAL("биометрические"),
    SOCIAL("общественные"),
    OTHER("иные"),
    NONE("NOT_AVAILABLE");

    override fun toString() = category
}