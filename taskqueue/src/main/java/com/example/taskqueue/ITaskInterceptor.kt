package com.example.taskqueue

interface ITaskInterceptor {
    fun init()

    fun handleNxt(cb: InterceptorCallback)
}