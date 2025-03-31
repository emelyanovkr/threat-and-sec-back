package org.threat.model.report

data class GeneralInformation(
    var customerName: String? = null,
    var category: SystemCategory? = null,
    var significance: GisSignificanceOptions? = null,
    var systemScale: GisScaleOptions? = null,
    var pdnCategory: IspdnCategoryOptions? = null,
    var ownWorker: Boolean? = null,
    var subjectCount: SubjectCountOptions? = null,
    var threatType: ThreatTypeOptions? = null,
    var kiiLevel: KiiLevelOptions? = null,
    var kiiSignificanceArea: KiiSignificanceOptions? = null,
    var kiiCategoryPick: String? = null,
    var kiiCategoryResult: String? = null,
) {
    enum class SystemCategory(private val systemName: String) {
        GIS("ГИС"),
        ISPDN("ИСПДн"),
        KII("КИИ");

        override fun toString() = systemName
    }

    enum class GisSignificanceOptions(private val significance: String) {
        FIRST("первый"),
        SECOND("второй"),
        THIRD("третий");

        override fun toString() = significance
    }

    enum class GisScaleOptions(private val scale: String) {
        FEDERAL("федеральный"),
        REGIONAL("региональный"),
        OBJECT("объектовый");

        override fun toString() = scale
    }

    enum class IspdnCategoryOptions(private val category: String) {
        SPEICAL("специальные"),
        BIOMETRICAL("биометрические"),
        SOCIAL("общественные"),
        OTHER("иные");

        override fun toString() = category
    }

    enum class SubjectCountOptions(private val count: String) {
        MORE100("более 100"),
        LESS100("менее 100");

        override fun toString() = count
    }

    enum class ThreatTypeOptions(private val threatType: String) {
        FIRST("1"),
        SECOND("2"),
        THIRD("3");

        override fun toString() = threatType
    }

    enum class KiiLevelOptions(private val kiiLevel: String) {
        LEVEL1("уровень 1"),
        LEVEL2("уровень 2"),
        LEVEL3("уровень 3"),
        LEVEL4("уровень 4"),
        LEVEL5("уровень 5");

        override fun toString() = kiiLevel
    }

    enum class KiiSignificanceOptions(private val significance: String) {
        SOCIAL("социальная значимость"),
        POLITICAL("политическая значимость"),
        ECONOMIC("экономическая значимость"),
        ECOLOGICAL("экологическая значимость"),
        DEFENSE("значимость для обеспечения обороны страны, безопасности государства и правопорядка");

        override fun toString() = significance
    }
}
