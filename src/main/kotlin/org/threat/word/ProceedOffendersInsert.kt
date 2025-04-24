package org.threat.word

import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart
import org.docx4j.wml.P
import org.docx4j.wml.Text
import org.threat.model.offenders.Offender
import org.threat.model.offenders.OffendersReasonsEntity
import org.threat.model.offenders.OffendersType
import org.threat.util.ReflectionUtils
import java.math.BigInteger

object ProceedOffendersInsert {
    private fun getReasonForOffender(offender: Offender): String {
        val offenderFromDb = Offender.find("name", offender.name).firstResult()
        if (offenderFromDb == null) {
            println("OFFENDER '${offender.name}' NOT FOUND")
        }
        val reasonEntity = OffendersReasonsEntity.find("offender", offenderFromDb!!).firstResult()

        return if (reasonEntity != null) {
            reasonEntity.reason
        } else {
            println("NOT FOUND REASON FOR OFFENDER: ${offender.name}")
            ""
        }
    }

    fun insertViolatorsInformation(
        wordProcessingPackage: WordprocessingMLPackage,
        violators: List<Offender>,
        offendersType: OffendersType
    ) {
        val mainPart = wordProcessingPackage.mainDocumentPart
        val paragraphs = ReflectionUtils.getAllElementsOfType(mainPart.jaxbElement, P::class.java)
        val placeholderParagraph = paragraphs.firstOrNull { p ->
            ReflectionUtils.getAllElementsOfType(p, Text::class.java)
                .any { it.value.contains(offendersType.placeholder) }
        }
        if (placeholderParagraph != null) {

            val contentList = mainPart.jaxbElement.content
            val idx = contentList.indexOf(placeholderParagraph)
            val placeholderStyle = placeholderParagraph.pPr

            contentList.remove(placeholderParagraph)

            var ndp = mainPart.numberingDefinitionsPart
            if (ndp == null) {
                ndp = NumberingDefinitionsPart()
                ndp.setJaxbElement(ndp.unmarshalDefaultNumbering())
                mainPart.addTargetPart(ndp)
            }

            val newNumIdLong = ndp.restart(offendersType.baseNumId, 0, 1)
            val newNumId = BigInteger.valueOf(newNumIdLong)

            val factory = Context.getWmlObjectFactory()

            val numberedParagraphs = violators.mapIndexed { index, offender ->
                val textValue = when (offendersType) {
                    OffendersType.EXCLUDED ->
                        "${offender.name} ${getReasonForOffender(offender)}"
                    OffendersType.CHOSEN ->
                        offender.name
                    OffendersType.NONE ->
                        "NOT_AVAILABLE"
                }

                ParagraphCreation.createNumberedBulletedParagraphWithStyleAndPunctuation(
                    factory,
                    textValue,
                    placeholderStyle,
                    newNumId,
                    index == violators.lastIndex
                )
            }

            contentList.addAll(idx, numberedParagraphs)
        }
    }

}