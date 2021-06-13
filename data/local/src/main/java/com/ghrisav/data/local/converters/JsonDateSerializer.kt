package com.ghrisav.data.local.converters

import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.util.*

class JsonDateSerializer {
    companion object {
        val SERIALIZER = JsonSerializer<Date> { src, _, _ ->
            JsonPrimitive(
                src?.time
            )
        }

        val DESERIALIZER = JsonDeserializer<Date> { json, _, _ ->
            json?.let {
                if (json.asString.isNotEmpty()) {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = json.asString.toLong()
                    calendar.time
                } else {
                    null
                }
            }
        }
    }
}