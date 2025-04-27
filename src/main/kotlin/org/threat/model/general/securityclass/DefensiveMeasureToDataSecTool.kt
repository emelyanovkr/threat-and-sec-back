package org.threat.model.general.securityclass

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "basic_defensive_measures_to_data_security_tools", schema = "defensive_measures_schema")
@IdClass(DefensiveMeasureToDataSecToolId::class)
class DefensiveMeasureToDataSecTool(
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measure_id", nullable = false)
    var measure: BasicDefensiveMeasure? = null,

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "security_tool_id", nullable = false)
    var securityTool: DataSecurityTool? = null

) : PanacheEntityBase {

    companion object : PanacheCompanion<DefensiveMeasureToDataSecTool>
}