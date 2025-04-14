package org.threat.model.general.securityclass

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.threat.model.general.SystemCategory

@Entity
@Table(name = "basic_defensive_measures")
@SequenceGenerator(name = "basic_def_meas_seq", sequenceName = "basic_defensive_measures_id_seq")
class BasicDefensiveMeasure(

    @Column(nullable = false, unique = true)
    var key: String = "",

    @Column(nullable = false)
    var name: String = "",

    var description: String = "",

    @Column(name = "system_category")
    @Enumerated(EnumType.STRING)
    var systemCategory: SystemCategory = SystemCategory.NONE,

    ) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<BasicDefensiveMeasure> {
        fun findBySecurityClassAndSystemCategory(securityClass: Int, systemCategory: SystemCategory): List<BasicDefensiveMeasure> {
            return find(
                "SELECT bdm " +
                        "FROM BasicDefensiveMeasure bdm " +
                        "JOIN DefensiveMeasureToSecurityClass dmsc " +
                        "ON bdm = dmsc.measure " +
                        "WHERE dmsc.securityClass = :securityClass " +
                        "AND bdm.systemCategory = :systemCategory",
                mapOf("securityClass" to securityClass, "systemCategory" to systemCategory)
            ).list()
        }
    }
}