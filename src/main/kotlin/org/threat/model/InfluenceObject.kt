package org.threat.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "influence_objects")
class InfluenceObject(var name: String = "", var alias: String = "") : PanacheEntity() {
    companion object : PanacheCompanion<InfluenceObject> {
        fun findByNameOrAlias(name: String): InfluenceObject? {
            find("name", name).firstResult()?.let { return it }
            return find("alias like ?1", "%$name%").firstResult()
        }
    }
}