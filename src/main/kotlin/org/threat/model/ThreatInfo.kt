package org.threat.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "threats_info")
class ThreatInfo(
    var name: String = "",
    var description: String = ""
) : PanacheEntityBase {
    @Id
    var id: Long? = null

    companion object : PanacheCompanion<ThreatInfo>
}