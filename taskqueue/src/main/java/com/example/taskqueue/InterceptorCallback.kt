package com.example.taskqueue

interface InterceptorCallback {
    fun nxt()

    fun skip()
}