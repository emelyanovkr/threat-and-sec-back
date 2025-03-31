package org.threat.model.threats

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@JsonPropertyOrder("id", "name", "description")
@Entity
@Table(name = "threats_info")
class ThreatInfo(
    var name: String = "",
    var description: String = ""
) : PanacheEntityBase {
    @Id
    var id: Long? = null

    companion object : PanacheCompanion<ThreatInfo> {
        fun findByInfluenceObjects(influenceObjects: List<String>): List<ThreatInfo> {
            return find(
                "SELECT DISTINCT t " +
                        "FROM ThreatInfo t " +
                        "JOIN ThreatToInfluenceObject rel ON rel.threat = t " +
                        "JOIN InfluenceObject io ON io = rel.influenceObject " +
                        "WHERE io.name in :names", mapOf("names" to influenceObjects)
            ).list()
        }
    }
}