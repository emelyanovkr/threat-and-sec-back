package org.threat.model.general

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "data_replacements")
@SequenceGenerator(name = "data_replacements_seq", sequenceName = "data_replacements_id_seq")
class DataReplacementEntity(

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", nullable = false)
    var category: SystemCategoryEntity? = null,

    @Column(nullable = false)
    var placeholder: String = "",

    @Column(nullable = false)
    var value: String = ""
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<DataReplacementEntity>
}