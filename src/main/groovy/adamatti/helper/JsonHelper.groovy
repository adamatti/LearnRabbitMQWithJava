package adamatti.helper

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

abstract class JsonHelper {
    static String toJsonString(Object obj){
        new JsonBuilder(obj).toPrettyString()
    }

    static Object toJsonObject(String jsonString){
        if (jsonString == null){
            return null
        }
        new JsonSlurper().parseText(jsonString)
    }
}
