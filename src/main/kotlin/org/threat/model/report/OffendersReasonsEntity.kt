package org.threat.model.report

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "offenders_reasons")
@SequenceGenerator(name = "offender_reasons_seq", sequenceName = "offenders_reasons_id_seq")
class OffendersReasonsEntity(
    @NotNull
    @Column(unique = true)
    var offender: String = "",

    @NotNull
    var reason: String = ""
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}