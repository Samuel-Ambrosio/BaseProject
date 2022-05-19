package com.samuelav.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

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