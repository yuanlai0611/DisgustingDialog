package com.example.taskqueueannotation.annotation


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Task(
        val name: String,
        val keep: Boolean = false
)