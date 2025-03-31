package org.threat.model.tactics

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "main_techniques")
@SequenceGenerator(name = "main_techniques_seq", sequenceName = "main_techniques_id_esq")
class MainTechnique(
    @Column(name="technique_number" ,nullable = false)
    var techniqueNumber: Int = 0,
    @Column(name="technique_description" ,nullable = false)
    var techniqueDescription: String = "") : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}