package org.threat.model.general

import com.fasterxml.jackson.annotation.JsonInclude
import org.threat.model.general.input.*

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class GeneralInformation(
    var customerName: String = "",
    var category: SystemCategory = SystemCategory.NONE,
    var gisSignificance: GisSignificanceOptions = GisSignificanceOptions.NONE,
    var gisSystemScale: GisScaleOptions = GisScaleOptions.NONE,
    var ispdnCategory: IspdnCategoryOptions = IspdnCategoryOptions.NONE,
    var ispdnOwnWorker: IspdnOwnWorkerOptions = IspdnOwnWorkerOptions.NONE,
    var ispdnSubjectCount: IspdnSubjectCountOptions = IspdnSubjectCountOptions.NONE,
    var ispdnThreatType: IspdnThreatTypeOptions = IspdnThreatTypeOptions.NONE,
    var kiiLevel: KiiLevelOptions = KiiLevelOptions.NONE,
    var kiiSignificanceArea: KiiSignificanceOptions = KiiSignificanceOptions.NONE,
    var kiiCategoryPick: String = "",
    var kiiSignificanceCategory: String = ""
)

