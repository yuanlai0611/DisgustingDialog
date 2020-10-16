package com.example.taskqueue

import kotlinx.coroutines.channels.Channel


class TaskQueue {


    private val taskQueue = Channel<Task>()

    fun test() {

    }

    @Synchronized fun getInstance() {

    }
}