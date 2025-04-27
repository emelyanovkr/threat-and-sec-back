package org.threat.model.offenders

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "offenders_power_levels", schema = "offenders_schema")
@SequenceGenerator(
    name = "offenders_power_levels_seq",
    sequenceName = "offenders_power_levels_id_seq"
)
class OffenderPowerLevel(
    @Column(name = "level_code", nullable = false, unique = true)
    var levelCode: String = "",

    @Column(name = "level_name", nullable = false, unique = true)
    var levelName: String = ""
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<OffenderPowerLevel>
}
