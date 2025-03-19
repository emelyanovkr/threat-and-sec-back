package org.threat.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "influence_objects")
class InfluenceObject(var name: String = "") : PanacheEntity() {
    companion object : PanacheCompanion<InfluenceObject> {
        fun findByName(name: String): InfluenceObject? {
            return find("name", name).firstResult()
        }
    }
}