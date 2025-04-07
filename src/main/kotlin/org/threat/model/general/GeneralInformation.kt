package org.threat.model.general

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

enum class GisSignificanceOptions(private val significance: String) {
    FIRST("первый"),
    SECOND("второй"),
    THIRD("третий"),
    NONE("NOT_AVAILABLE");

    override fun toString() = significance
}

enum class GisScaleOptions(private val scale: String) {
    FEDERAL("федеральный"),
    REGIONAL("региональный"),
    OBJECT("объектовый"),
    NONE("NOT_AVAILABLE");

    override fun toString() = scale
}

enum class IspdnCategoryOptions(private val category: String) {
    SPEICAL("специальные"),
    BIOMETRICAL("биометрические"),
    SOCIAL("общественные"),
    OTHER("иные"),
    NONE("NOT_AVAILABLE");

    override fun toString() = category
}

enum class IspdnOwnWorkerOptions(private val worker: String) {
    YES("да"),
    NO("нет"),
    NO_MATTER("не важно"),
    NONE("NOT_AVAILABLE");

    override fun toString() = worker
}

enum class IspdnSubjectCountOptions(private val count: String) {
    MORE100("более 100"),
    LESS100("менее 100"),
    ANY("любое"),
    NONE("NOT_AVAILABLE");

    override fun toString() = count
}

enum class IspdnThreatTypeOptions(private val threatType: Int) {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    NONE(0);

    override fun toString() = threatType.toString()
}

enum class KiiLevelOptions(private val kiiLevel: String) {
    LEVEL1("уровень 1"),
    LEVEL2("уровень 2"),
    LEVEL3("уровень 3"),
    LEVEL4("уровень 4"),
    LEVEL5("уровень 5"),
    NONE("NOT_AVAILABLE");

    override fun toString() = kiiLevel
}

enum class KiiSignificanceOptions(private val significance: String) {
    SOCIAL("социальная значимость"),
    POLITICAL("политическая значимость"),
    ECONOMIC("экономическая значимость"),
    ECOLOGICAL("экологическая значимость"),
    DEFENSE("значимость для обеспечения обороны страны, безопасности государства и правопорядка"),
    NONE("NOT_AVAILABLE");

    override fun toString() = significance
}
