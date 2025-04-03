package org.threat.util

import jakarta.xml.bind.JAXBElement
import org.docx4j.wml.ContentAccessor
import kotlin.reflect.full.memberProperties

object ReflectionUtils {
    fun <T> getAllElementsOfType(obj: Any, toSearch: Class<T>): List<T> {
        val result = mutableListOf<T>()

        val realObj = if (obj is JAXBElement<*>) obj.value else obj

        if (toSearch.isInstance(realObj)) {
            result.add(toSearch.cast(realObj))
        }
        if (realObj is ContentAccessor) {
            for (child in realObj.content) {
                result.addAll(getAllElementsOfType(child, toSearch))
            }
        }
        return result
    }

    fun <T : Any> T.toMap(): Map<String, String> {
        return this::class.memberProperties.associate { prop ->
            prop.name to (prop.getter.call(this)?.toString() ?: "")
        }
    }
}