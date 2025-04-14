package org.threat.dto

import org.threat.model.general.SystemCategory
import org.threat.model.general.input.*

data class SecurityClassRequest(
    var systemCategory: SystemCategory = SystemCategory.NONE,

    var gisScale: GisScaleOptions? = null,
    var gisSignificance: GisSignificanceOptions? = null,

    var pdCategory: IspdnCategoryOptions? = null,
    var ownWorker: IspdnOwnWorkerOptions? = null,
    var subjectCount: IspdnSubjectCountOptions? = null,
    var threatType: IspdnThreatTypeOptions? = null,

    var kiiSecurityClass: Int? = null,
)