package com.example.taskqueue

import kotlin.reflect.KClass
import kotlin.reflect.full.safeCast

/**
 * 携带数据
 */
class Instruction {
    companion object {
        private const val EMPTY_TASK_NAME = "empty_task_name"
    }

    private val mBucket = mutableMapOf<String, Any>()
    var taskName = EMPTY_TASK_NAME

    fun putInt(key: String, value: Int) {
        mBucket[key] = value
    }

    fun putFloat(key: String, value: Float) {
        mBucket[key] = value
    }

    fun putLong(key: String, value: Long) {
        mBucket[key] = value
    }

    fun putDouble(key: String, value: Double) {
        mBucket[key] = value
    }

    fun putObject(key: String, value: Any) {
        mBucket[key] = value
    }

    fun getInt(key: String) =
        mBucket[key] as? Int

    fun getFloat(key: String) =
        mBucket[key] as? Float

    fun getLong(key: String) =
        mBucket[key] as? Long

    fun getDouble(key: String) =
        mBucket[key] as? Double

    fun <T : Any> getObject(key: String, clazz: KClass<T>) =
        clazz.safeCast(mBucket[key])

    fun getIntOrDefault(key: String, default: Int) =
        getInt(key) ?: default

    fun getFloatOrDefault(key: String, default: Float) =
        getFloat(key) ?: default

    fun getLongOrDefault(key: String, default: Long) =
        getLong(key) ?: default

    fun getDoubleOrDefault(key: String, default: Double) =
        getDouble(key) ?: default

    fun <T : Any> getObjectOrDefault(key: String, default: T) =
        getObject(key, default.javaClass.kotlin) ?: default

}