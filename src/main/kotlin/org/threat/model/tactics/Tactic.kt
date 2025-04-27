package org.threat.model.tactics

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "tactics", schema ="tactics_techniques_schema")
@SequenceGenerator(name = "tactics_seq", sequenceName = "tactics_id_seq")
class Tactic(
    @Column(name = "tactic_name", nullable = false)
    var tacticName: String = "",

    @Column(name = "tactical_task", nullable = false)
    var tacticalTask: String = "",
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}