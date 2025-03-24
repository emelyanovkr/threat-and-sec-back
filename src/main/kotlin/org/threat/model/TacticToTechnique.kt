package org.threat.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "tactics_to_techniques")
class TacticToTechnique(
    @EmbeddedId
    var id: TacticToTechniqueId,

    @ManyToOne
    @MapsId("tacticId")
    @JoinColumn(name = "tactic_id", nullable = false)
    var tactic: Tactic,

    @ManyToOne
    @MapsId("techniqueId")
    @JoinColumn(name = "technique_id", nullable = false)
    var technique: MainTechnique
) : PanacheEntityBase {

    constructor(tactic: Tactic, technique: MainTechnique) : this(
        id = TacticToTechniqueId(),
        tactic = tactic,
        technique = technique
    )

    constructor() : this(
        TacticToTechniqueId(),
        Tactic(),
        MainTechnique()
    )

    companion object : PanacheCompanion<TacticToTechnique>

}