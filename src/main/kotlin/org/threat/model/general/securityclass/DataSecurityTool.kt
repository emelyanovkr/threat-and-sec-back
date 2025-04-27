package org.threat.model.general.securityclass

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "data_security_tools", schema = "defensive_measures_schema")
@SequenceGenerator(name = "data_sec_tools_seq", sequenceName = "data_security_tools_id_seq")
class DataSecurityTool(
    var vendor: String = "",

    @Column(nullable = false, unique = true)
    var name: String = ""
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<DataSecurityTool>
}