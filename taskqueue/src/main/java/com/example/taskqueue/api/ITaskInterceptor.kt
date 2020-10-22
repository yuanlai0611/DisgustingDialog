package com.example.taskqueue.api

import com.example.taskqueue.Instruction

interface ITaskInterceptor {
    fun intercept(taskName: String, instruction: Instruction): Boolean
}