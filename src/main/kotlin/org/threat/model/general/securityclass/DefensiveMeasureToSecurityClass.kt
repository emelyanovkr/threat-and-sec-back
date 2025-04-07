package org.threat.model.general.securityclass

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "defensive_measures_to_security_class")
@IdClass(DefensiveMeasureToSecurityClassId::class)
class DefensiveMeasureToSecurityClass(

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measure_id", nullable = false)
    var measure: BasicDefensiveMeasure? = null,

    @Id
    @Column(name = "security_class", nullable = false)
    var securityClass: Int = 0

) : PanacheEntityBase {

    companion object : PanacheCompanion<DefensiveMeasureToSecurityClass>
}