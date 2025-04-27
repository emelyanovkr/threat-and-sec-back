package org.threat.model.risks

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.threat.model.general.SystemCategoryEntity

@Entity
@Table(name = "consequences", schema = "threat_and_sec_schema")
@SequenceGenerator(name = "consequences_seq", sequenceName = "consequences_id_seq")
class Consequence(

    @Column(nullable = false)
    var title: String = "",

    @Column(nullable = false)
    var description: String = ""
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_id", nullable = false)
    var risk: Risk = Risk()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_category", nullable = false)
    var systemCategory: SystemCategoryEntity? = null

    companion object : PanacheCompanion<Consequence>
}