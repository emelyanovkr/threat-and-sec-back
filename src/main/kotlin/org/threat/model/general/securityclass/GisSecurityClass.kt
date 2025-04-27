package org.threat.model.general.securityclass

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.threat.model.general.input.GisScaleOptions
import org.threat.model.general.input.GisSignificanceOptions


@Entity
@Table(name = "gis_security_class", schema = "defensive_measures_schema")
@SequenceGenerator(name = "gis_security_class_seq", sequenceName = "gis_security_class_id_seq")
class GisSecurityClass(

    @Enumerated(EnumType.STRING)
    @Column(name = "region_scale", nullable = false)
    var scale: GisScaleOptions = GisScaleOptions.NONE,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var significance: GisSignificanceOptions = GisSignificanceOptions.NONE,

    @Column(name = "security_class", nullable = false)
    var securityClass: Int = 0

) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<GisSecurityClass> {
        fun findByOptions(scale: GisScaleOptions, significance: GisSignificanceOptions): GisSecurityClass? {
            return find(
                "scale = :scale AND significance = :significance",
                mapOf("scale" to scale, "significance" to significance)
            ).firstResult()
        }
    }
}