package com.jesen.cod.common

import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preferences<T>(val context:Context, val name:String, val default:T, val preName:String = "default")
    // 读写代理
    :ReadWriteProperty<Any?, T> {

    private val prefs by lazy {
        context.getSharedPreferences(preName, Context.MODE_PRIVATE)
    }

    /**
     * Returns the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @return the property value.
     */
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreferences(name)
    }

    private fun findPreferences(key: String):T {

        return when(default){
            is Long -> prefs.getLong(key, default)
            is Int -> prefs.getInt(key,default)
            is Boolean -> prefs.getBoolean(key,default)
            is String ->prefs.getString(key, default)
            else -> throw IllegalArgumentException("Unsupported type.")
        } as T
    }

    /**
     * Sets the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @param value the value to set.
     */
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreferences(name, value)
    }

    private fun putPreferences(key:String, value: T){
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key,value)
                is Int -> putInt(key,value)
                is Boolean -> putBoolean(key,value)
                is String ->putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
        }.apply()
    }
}