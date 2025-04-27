package org.threat.model.risks

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "risks", schema = "threat_and_sec_schema")
@SequenceGenerator(name = "risks_seq", sequenceName = "risks_id_seq")
class Risk(
    @Column(unique = true, nullable = false)
    var code: String = "",

    @Column(unique = true, nullable = false)
    var name: String = ""
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<Risk>
}