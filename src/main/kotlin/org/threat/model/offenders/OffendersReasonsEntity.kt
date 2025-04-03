package org.threat.model.offenders

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "offenders_reasons")
@SequenceGenerator(name = "offender_reasons_seq", sequenceName = "offenders_reasons_id_seq")
class OffendersReasonsEntity(

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "offender", nullable = false)
    var offender: Offenders? = null,

    @Column(nullable = false)
    var reason: String = ""
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<OffendersReasonsEntity>
}