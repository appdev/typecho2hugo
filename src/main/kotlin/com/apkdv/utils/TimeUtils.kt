package com.apkdv.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


object TimeUtils {
    //1529898283
    fun millis2String(millis: Long): String {
        return getSafeDateFormat("yyyy-MM-dd").format(Date(millis*1000));
    }
    private val SDF_THREAD_LOCAL:ThreadLocal<HashMap<String, SimpleDateFormat>> by lazy {
            return@lazy  object : ThreadLocal<HashMap<String, SimpleDateFormat>>() {
                override fun initialValue(): HashMap<String, SimpleDateFormat> {
                    return HashMap()
                }
            }
    }

    private fun getSafeDateFormat(pattern: String): SimpleDateFormat {
        var simpleDateFormat = SDF_THREAD_LOCAL.get()[pattern]
        if (simpleDateFormat == null) {
            simpleDateFormat = SimpleDateFormat(pattern)
            SDF_THREAD_LOCAL.get()[pattern] = simpleDateFormat
        }
        return simpleDateFormat
    }

}