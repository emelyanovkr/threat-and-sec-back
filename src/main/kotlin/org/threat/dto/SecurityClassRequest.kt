package org.threat.dto

import org.threat.model.general.SystemCategory
import org.threat.model.general.input.*

data class SecurityClassRequest(
    var systemCategory: SystemCategory = SystemCategory.NONE,

    var gisScale: GisScaleOptions? = null,
    var gisSignificance: GisSignificanceOptions? = null,

    var ispdnCategory: IspdnCategoryOptions? = null,
    var ispdnOwnWorker: IspdnOwnWorkerOptions? = null,
    var ispdnSubjectCount: IspdnSubjectCountOptions? = null,
    var ispdnThreatType: IspdnThreatTypeOptions? = null,

    var kiiSignificanceCategory: Int? = null,
)