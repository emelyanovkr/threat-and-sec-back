package org.threat.dto.risks

data class ConsequenceDTO(val id: Long, val title: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConsequenceDTO

        return title == other.title
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}
