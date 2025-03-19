package org.threat.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "threats_to_influence_objects", schema = "threat_and_sec_schema")
data class ThreatToInfluenceObject(
    @EmbeddedId
    var id: ThreatToInfluenceId,

    @ManyToOne
    @MapsId("threatId")
    @JoinColumn(name = "threat_id", nullable = false)
    var threat: ThreatInfo,

    @ManyToOne
    @MapsId("influenceObjectId")
    @JoinColumn(name = "influence_object_id", nullable = false)
    var influenceObject: InfluenceObject
) : PanacheEntityBase {

    constructor(threat: ThreatInfo, influenceObject: InfluenceObject) : this(
        id = ThreatToInfluenceId(
            threatId = threat.id ?: throw IllegalArgumentException("Threat id не может быть null"),
            influenceObjectId = influenceObject.id
                ?: throw IllegalArgumentException("InfluenceObject id не может быть null")
        ),
        threat = threat,
        influenceObject = influenceObject
    )

    constructor() : this(
        ThreatToInfluenceId(),
        ThreatInfo(),
        InfluenceObject()
    )
}