package org.threat.dto.tactics

data class TechniqueDTO(
    val id: Long,
    val techniqueNumber: Int,
    val techniqueDescription: String
)

data class TacticDTO(
    val tacticId: Long,
    val tacticName: String,
    val tacticalTask: String,
    val techniques: List<TechniqueDTO>
)