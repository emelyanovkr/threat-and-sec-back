package org.threat.dto

import org.threat.model.general.*

data class SecurityClassRequest(
    var systemCategory: SystemCategory? = null,

    var gisScale: GisScaleOptions? = null,
    var gisSignificanceOptions: GisSignificanceOptions? = null,

    var pdCategory: IspdnCategoryOptions? = null,
    var ownWorker: IspdnOwnWorkerOptions? = null,
    var subjectCount: IspdnSubjectCountOptions? = null,
    var threatType: IspdnThreatTypeOptions? = null,

    var kiiSecurityClass: Int? = null,
)