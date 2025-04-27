package org.threat.model.general

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "system_categories", schema = "threat_and_sec_schema")
class SystemCategoryEntity(
    @Column(unique = true, nullable = false)
    var name: String = ""
) : PanacheEntity() {
    companion object : PanacheCompanion<SystemCategoryEntity>
}