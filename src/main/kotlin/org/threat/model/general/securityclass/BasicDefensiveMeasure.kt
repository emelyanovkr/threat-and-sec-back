package org.threat.model.general.securityclass

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "basic_defensive_measures")
@SequenceGenerator(name = "basic_def_meas_seq", sequenceName = "basic_defensive_measures_id_seq")
class BasicDefensiveMeasure(

    @Column(nullable = false, unique = true)
    var key: String = "",

    @Column(nullable = false)
    var name: String = ""

) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<BasicDefensiveMeasure> {
        fun findBySecurityClass(securityClass: Int): List<BasicDefensiveMeasure> {
            return find(
                "SELECT bdm " +
                        "FROM BasicDefensiveMeasure bdm " +
                        "JOIN DefensiveMeasureToSecurityClass dmsc " +
                        "ON bdm = dmsc.measure WHERE dmsc.securityClass = ?1",
                securityClass
            ).list()
        }
    }
}