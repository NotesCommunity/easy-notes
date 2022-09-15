package com.hadiyarajesh.easynotes.database.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class ListConverter {
    private val adapter by lazy {
        val moshi = Moshi.Builder().build()
        val list: ParameterizedType =
            Types.newParameterizedType(List::class.java, String::class.java)
        return@lazy moshi.adapter<List<String>>(list)
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        return adapter.toJson(list)
    }

    @TypeConverter
    fun toList(jsonStr: String?): List<String>? {
        return jsonStr?.let { adapter.fromJson(jsonStr) }
    }
}
