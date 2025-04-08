package org.threat.model.general.securityclass

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.threat.model.general.input.IspdnCategoryOptions
import org.threat.model.general.input.IspdnOwnWorkerOptions
import org.threat.model.general.input.IspdnSubjectCountOptions
import org.threat.model.general.input.IspdnThreatTypeOptions

@Entity
@Table(name = "ispdn_security_class")
@SequenceGenerator(name = "ispdn_security_class_seq", sequenceName = "ispdn_security_class_id_seq")
class IspdnSecurityClass(

    @Enumerated(EnumType.STRING)
    @Column(name = "pd_category", nullable = false)
    var pdCategory: IspdnCategoryOptions = IspdnCategoryOptions.NONE,

    @Enumerated(EnumType.STRING)
    @Column(name = "own_worker_yes_no", nullable = false)
    var ownWorkerYesNo: IspdnOwnWorkerOptions = IspdnOwnWorkerOptions.NONE,

    @Enumerated(EnumType.STRING)
    @Column(name = "subjects_count", nullable = false)
    var subjectsCount: IspdnSubjectCountOptions = IspdnSubjectCountOptions.NONE,

    @Enumerated(EnumType.STRING)
    @Column(name = "threat_type", nullable = false)
    var threatType: IspdnThreatTypeOptions = IspdnThreatTypeOptions.NONE,

    @Column(name = "security_class", nullable = false)
    var securityClass: Int = 0
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<IspdnSecurityClass> {
        fun findByOptions(
            pdCategory: IspdnCategoryOptions,
            ownWorkerYesNo: IspdnOwnWorkerOptions,
            subjectsCount: IspdnSubjectCountOptions,
            threatType: IspdnThreatTypeOptions
        ): IspdnSecurityClass? {
            return find(
                "pdCategory = :pdCategory and ownWorkerYesNo = :ownWorkerYesNo and subjectsCount = :subjectsCount and threatType = :threatType",
                mapOf(
                    "pdCategory" to pdCategory,
                    "ownWorkerYesNo" to ownWorkerYesNo,
                    "subjectsCount" to subjectsCount,
                    "threatType" to threatType
                )
            ).firstResult()
        }

    }
}