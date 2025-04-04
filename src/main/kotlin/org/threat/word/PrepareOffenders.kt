package org.threat.word

import org.docx4j.XmlUtils
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.PPr
import org.docx4j.wml.Text
import org.threat.model.offenders.Offenders
import org.threat.model.offenders.OffendersReasonsEntity
import org.threat.model.offenders.OffendersType
import org.threat.util.ReflectionUtils
import java.math.BigInteger

object PrepareOffenders {
    private fun getReasonForOffender(offender: Offenders): String {
        val offenderFromDb = Offenders.find("name", offender.name).firstResult()
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
        violators: List<Offenders>,
        violatorsType: OffendersType
    ) {
        val mainPart = wordProcessingPackage.mainDocumentPart
        val paragraphs = ReflectionUtils.getAllElementsOfType(mainPart.jaxbElement, P::class.java)
        val placeholderParagraph = paragraphs.firstOrNull { p ->
            ReflectionUtils.getAllElementsOfType(p, Text::class.java)
                .any { it.value.contains(violatorsType.placeholder) }
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

            val newNumIdLong = ndp.restart(violatorsType.baseNumId, 0, 1)
            val newNumId = BigInteger.valueOf(newNumIdLong)

            val factory = Context.getWmlObjectFactory()

            val numberedParagraphs = violators.mapIndexed { _, offender ->
                val reason = getReasonForOffender(offender)
                val textValue = "${offender.name} $reason"
                ParagraphCreationUtil.createNumberedBulletedParagraphWithStyle(factory, textValue, placeholderStyle, newNumId)
            }

            contentList.addAll(idx, numberedParagraphs)
        }
    }

}