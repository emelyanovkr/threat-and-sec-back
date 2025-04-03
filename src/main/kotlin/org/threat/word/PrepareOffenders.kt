package org.threat.word

import org.docx4j.XmlUtils
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.PPr
import org.docx4j.wml.Text
import org.threat.model.offenders.Offenders
import org.threat.model.offenders.OffendersReasonsEntity
import org.threat.util.ReflectionUtils
import java.math.BigInteger
import java.util.*

object PrepareOffenders {
    private fun getReasonForOffender(offender: Offenders): String {
        val offenderFromDb = Offenders.find("name", offender.name).firstResult()
        val reasonEntity = OffendersReasonsEntity.find("offender", offenderFromDb!!).firstResult()
        return reasonEntity?.reason ?: ""
    }

    fun insertViolatorsInformation(wordProcessingPackage: WordprocessingMLPackage, violators: List<Offenders>) {
        val mainPart = wordProcessingPackage.mainDocumentPart
        val paragraphs = ReflectionUtils.getAllElementsOfType(mainPart.jaxbElement, P::class.java)
        val placeholderParagraph = paragraphs.firstOrNull { p ->
            ReflectionUtils.getAllElementsOfType(p, Text::class.java)
                .any { it.value.contains("\${violators_information}") }
        }
        if (placeholderParagraph != null) {
            placeholderParagraph.content.clear()

            val contentList = mainPart.jaxbElement.content
            val idx = contentList.indexOf(placeholderParagraph)

            val placeholderStyle = placeholderParagraph.pPr
            contentList.remove(placeholderParagraph)

            val factory = Context.getWmlObjectFactory()

            val numberedParagraphs = violators.mapIndexed { _, offender ->
                val reason = getReasonForOffender(offender)
                val offenderNameWithLowerCase =
                    offender.name.replaceFirstChar { it.lowercase(Locale.forLanguageTag("ru-RU")) }
                val textValue = "$offenderNameWithLowerCase $reason"
                createNumberedParagraphWithStyle(factory, textValue, placeholderStyle)
            }

            contentList.addAll(idx, numberedParagraphs)
        }
    }

    private fun createNumberedParagraphWithStyle(
        factory: ObjectFactory,
        textValue: String,
        placeholderStyle: PPr?
    ): P {
        val p = factory.createP()

        val newPPr = if (placeholderStyle != null) {
            XmlUtils.deepCopy(placeholderStyle) as PPr
        } else {
            factory.createPPr()
        }

        val numPr = factory.createPPrBaseNumPr()
        val ilvl = factory.createPPrBaseNumPrIlvl()
        ilvl.`val` = BigInteger.ZERO
        numPr.ilvl = ilvl

        val numId = factory.createPPrBaseNumPrNumId()
        numId.`val` = BigInteger.ONE
        numPr.numId = numId

        newPPr.numPr = numPr
        p.pPr = newPPr

        val newInd = factory.createPPrBaseInd()
        newInd.left = BigInteger.ZERO
        newInd.firstLine = BigInteger.valueOf(709)
        newPPr.ind = newInd

        val r = factory.createR()
        val rPr = factory.createRPr()
        val sz = factory.createHpsMeasure()
        sz.`val` = BigInteger.valueOf(24)
        rPr.sz = sz
        rPr.szCs = sz
        r.rPr = rPr

        val t = factory.createText()
        t.value = textValue
        t.space = "preserve"
        r.content.add(t)
        p.content.add(r)

        return p
    }
}