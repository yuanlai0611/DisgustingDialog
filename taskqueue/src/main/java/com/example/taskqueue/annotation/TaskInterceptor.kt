package com.example.taskqueue.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class TaskInterceptor(
        val taskName: String
)