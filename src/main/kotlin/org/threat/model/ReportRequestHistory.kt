package org.threat.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.threat.model.general.SystemCategory
import java.time.OffsetDateTime

@Entity
@Table(name = "report_request_history", schema = "report_generation_data_schema")
@SequenceGenerator(name = "report_request_id_seq", sequenceName = "report_request_history_id_seq")
class ReportRequestHistory(
    @ColumnDefault("now()")
    @Column(name = "created_at")
    var createdAt: OffsetDateTime = OffsetDateTime.now(),

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    var payload: String = "",

    @Column(name = "system_category")
    @Enumerated(EnumType.STRING)
    var systemCategory: SystemCategory = SystemCategory.NONE,
) : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object : PanacheCompanion<ReportRequestHistory>
}