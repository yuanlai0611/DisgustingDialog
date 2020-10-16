package com.example.taskqueue.annotation


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class Task(
        val name: String,
        val automatic: Boolean = false,
        val priority: Int = -1
)