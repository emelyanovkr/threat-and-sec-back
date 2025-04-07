package org.threat.word

import org.docx4j.XmlUtils
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.PPr
import java.math.BigInteger

object ParagraphCreation {

    fun createNumberedBulletedParagraphWithStyleAndPunctuation(
        factory: ObjectFactory,
        textValue: String,
        placeholderStyle: PPr?,
        newNumId: BigInteger,
        isLastObject: Boolean
    ): P {
        val punctuation = if (isLastObject) "." else ";"
        val finalText = "$textValue$punctuation"
        return createNumberedBulletedParagraphWithStyle(factory, finalText, placeholderStyle, newNumId)
    }

    private fun createNumberedBulletedParagraphWithStyle(
        factory: ObjectFactory,
        textValue: String,
        placeholderStyle: PPr?,
        newNumId: BigInteger
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
        numId.`val` = newNumId
        numPr.numId = numId

        newPPr.numPr = numPr
        p.pPr = newPPr

        val newInd = factory.createPPrBaseInd()
        newInd.left = BigInteger.ZERO
        newInd.firstLine = BigInteger.valueOf(709)
        newPPr.ind = newInd

        val r = factory.createR()
        val t = factory.createText()
        t.value = textValue
        t.space = "preserve"
        r.content.add(t)
        p.content.add(r)

        return p
    }

}