package com.aminfaruq.dicodingevent.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

open class Event<out T>(private val content: T) {
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
    fun peekContent(): T = content
}

fun Fragment.saveToBundle(outState: Bundle, key: String, value: Any) {
    when (value) {
        is String -> outState.putString(key, value)
        is Int -> outState.putInt(key, value)
        is Boolean -> outState.putBoolean(key, value)
        is Float -> outState.putFloat(key, value)
        is Long -> outState.putLong(key, value)
        is Parcelable -> outState.putParcelable(key, value)
        is Serializable -> outState.putSerializable(key, value)
        // Tambahkan tipe data lain jika diperlukan
        else -> throw IllegalArgumentException("Unsupported data type")
    }
}
