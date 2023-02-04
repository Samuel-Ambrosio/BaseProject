package com.samuelav.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? = value?.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
    }
    @TypeConverter
    fun localDateTimeToTimestamp(date: LocalDateTime?): Long? = date?.atZone(ZoneId.systemDefault())?.toEpochSecond()

    @TypeConverter
    fun longListToJson(list: List<Long>?): String = Gson().toJson(list)

    @TypeConverter
    fun jsonToLongList(jsonList: String): List<Long>? = Gson().fromJson(jsonList)

    @TypeConverter
    fun stringListToJson(list: List<String>?): String = Gson().toJson(list)

    @TypeConverter
    fun jsonToStringList(jsonList: String): List<String>? = Gson().fromJson(jsonList)

    private inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, genericType<T>())
    private inline fun <reified T> genericType() : Type? = object: TypeToken<T>() {}.type
}