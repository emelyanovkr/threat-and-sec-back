package org.threat.word

import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.P
import org.docx4j.wml.Text
import org.threat.model.threats.ThreatsExecutionMethod
import org.threat.util.ReflectionUtils
import java.math.BigInteger

object PrepareThreatMethods {

    fun insertThreatMethods(wordProcessingPackage: WordprocessingMLPackage, methods: List<ThreatsExecutionMethod>) {
        val mainPart = wordProcessingPackage.mainDocumentPart

        val paragraphs = ReflectionUtils.getAllElementsOfType(mainPart.jaxbElement, P::class.java)
        val placeholderParagraph = paragraphs.firstOrNull { p ->
            ReflectionUtils.getAllElementsOfType(p, Text::class.java)
                .any { it.value.contains("\${threats_execution_methods}") }
        }

        if (placeholderParagraph != null) {
            val contentList = mainPart.jaxbElement.content
            val idx = contentList.indexOf(placeholderParagraph)
            val placeholderStyle = placeholderParagraph.pPr

            contentList.remove(placeholderParagraph)

            val factory = Context.getWmlObjectFactory()

            val bulletNumId = BigInteger.valueOf(3)
            val bulletParagraphs = methods.map { method ->
                ParagraphCreationUtil.createNumberedBulletedParagraphWithStyle(
                    factory,
                    method.name,
                    placeholderStyle,
                    bulletNumId
                )
            }

            contentList.addAll(idx, bulletParagraphs)
        }
    }
}