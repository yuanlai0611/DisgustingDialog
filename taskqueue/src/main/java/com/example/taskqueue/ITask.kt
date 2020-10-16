package com.example.taskqueue

interface ITask {
    // 用于初始化资源
    fun init()

    fun onTrigger()

    // 用于回收资源
    fun endTask()
}