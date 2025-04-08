package org.threat.model.general

import org.threat.model.general.input.*

data class GeneralInformation(
    var customerName: String = "",
    var category: SystemCategory = SystemCategory.NONE,
    var significance: GisSignificanceOptions = GisSignificanceOptions.NONE,
    var systemScale: GisScaleOptions = GisScaleOptions.NONE,
    var pdnCategory: IspdnCategoryOptions = IspdnCategoryOptions.NONE,
    var ownWorker: Boolean = false,
    var subjectCount: IspdnSubjectCountOptions = IspdnSubjectCountOptions.NONE,
    var threatType: IspdnThreatTypeOptions = IspdnThreatTypeOptions.NONE,
    var kiiLevel: KiiLevelOptions = KiiLevelOptions.NONE,
    var kiiSignificanceArea: KiiSignificanceOptions = KiiSignificanceOptions.NONE,
    var kiiCategoryPick: String = "",
    var kiiCategoryResult: String = ""
)

