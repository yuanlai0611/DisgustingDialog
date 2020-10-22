package com.example.taskqueueannotation.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class TaskInterceptor(
        val taskNames: Array<String>
)