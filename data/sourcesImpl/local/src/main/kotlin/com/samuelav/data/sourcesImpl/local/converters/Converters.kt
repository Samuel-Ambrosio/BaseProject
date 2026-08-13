package com.samuelav.data.sourcesImpl.local.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    private val moshi = Moshi.Builder().build()
    private val longListAdapter =
        moshi.adapter<List<Long>>(Types.newParameterizedType(List::class.java, Long::class.javaObjectType))
    private val stringListAdapter =
        moshi.adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java))

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? =
        value?.let {
            LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
        }

    @TypeConverter
    fun localDateTimeToTimestamp(date: LocalDateTime?): Long? = date?.atZone(ZoneId.systemDefault())?.toEpochSecond()

    @TypeConverter
    fun longListToJson(list: List<Long>?): String = longListAdapter.toJson(list)

    @TypeConverter
    fun jsonToLongList(jsonList: String): List<Long>? = longListAdapter.fromJson(jsonList)

    @TypeConverter
    fun stringListToJson(list: List<String>?): String = stringListAdapter.toJson(list)

    @TypeConverter
    fun jsonToStringList(jsonList: String): List<String>? = stringListAdapter.fromJson(jsonList)
}
