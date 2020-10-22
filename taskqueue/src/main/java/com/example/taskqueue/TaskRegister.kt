package com.example.taskqueue

import com.example.taskqueue.api.ITask
import com.example.taskqueueannotation.annotation.ITaskRegister
import kotlin.reflect.KClass
import kotlin.reflect.full.safeCast

// read task registration from generated class
internal object TaskRegister {
    private const val TASK_REGISTER_GEN_PATH = "TaskRegister_gen"
    private val mTaskMap = loadTaskRegisterGen()?.taskPathMap

    private fun loadTaskRegisterGen() = loadSingleClazz(TASK_REGISTER_GEN_PATH, ITaskRegister::class)

    private fun <T : Any> loadSingleClazz(clazzPath: String, type: KClass<T>): T? {
        val result = runCatching {
            val clazz = Class.forName(clazzPath)
            type.safeCast(clazz.newInstance())
        }
        return result.getOrNull()
    }

    fun provideTask(taskName: String) =
        mTaskMap?.get(taskName)?.let {
            loadSingleClazz(it, ITask::class)
        }
}