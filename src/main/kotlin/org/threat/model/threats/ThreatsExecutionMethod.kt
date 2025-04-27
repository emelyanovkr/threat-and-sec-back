package org.threat.model.threats

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name="threats_execution_methods", schema = "threat_and_sec_schema")
@SequenceGenerator(name = "threats_exec_seq", sequenceName = "threats_execution_methods_id_seq")
data class ThreatsExecutionMethod(var name: String = "") : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<ThreatsExecutionMethod>
}