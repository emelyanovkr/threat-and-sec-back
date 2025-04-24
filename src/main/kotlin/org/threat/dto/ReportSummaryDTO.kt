package org.threat.dto

import org.threat.model.general.SystemCategory
import org.threat.model.general.input.GisScaleOptions
import org.threat.model.general.input.GisSignificanceOptions
import org.threat.model.general.input.IspdnCategoryOptions
import org.threat.model.general.input.IspdnOwnWorkerOptions
import org.threat.model.general.input.IspdnSubjectCountOptions
import org.threat.model.general.input.IspdnThreatTypeOptions
import org.threat.model.general.input.KiiLevelOptions
import org.threat.model.general.input.KiiSignificanceOptions
import java.time.OffsetDateTime

data class ReportSummaryDTO(
    val id: Long,
    val createdAt: OffsetDateTime,
    val systemCategory: SystemCategory,

    // GIS
    val gisScale: GisScaleOptions? = null,
    val gisSignificance: GisSignificanceOptions? = null,

    // ISPDN
    val ispdnCategory: IspdnCategoryOptions? = null,
    val ispdnOwnWorker: IspdnOwnWorkerOptions? = null,
    val ispdnSubjectsCount: IspdnSubjectCountOptions? = null,
    val ispdnThreatType: IspdnThreatTypeOptions? = null,

    // KII
    val kiiSignificanceCategory: String? = null,
    val kiiLevel: KiiLevelOptions? = null,
    val kiiSignificanceArea: KiiSignificanceOptions? = null,
)