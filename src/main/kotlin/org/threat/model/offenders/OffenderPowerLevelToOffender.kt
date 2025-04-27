package org.threat.model.offenders

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "offenders_power_levels_to_offenders", schema = "offenders_schema")
@IdClass(OffenderPowerLevelToOffenderId::class)
class OffendersPowerLevelToOffender(

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "power_level_id", nullable = false)
    var powerLevel: OffenderPowerLevel? = null,

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "offender_id", nullable = false)
    var offender: Offender? = null
) : PanacheEntityBase {

    companion object : PanacheCompanion<OffendersPowerLevelToOffender>
}