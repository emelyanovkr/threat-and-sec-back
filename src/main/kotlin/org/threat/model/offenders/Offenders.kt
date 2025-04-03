package org.threat.model.offenders

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "offenders")
@SequenceGenerator(name = "offenders_seq", sequenceName = "offenders_id_seq")
class Offenders(var name: String = "") : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<Offenders>
}