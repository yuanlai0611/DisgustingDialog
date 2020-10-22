package com.example.taskqueue.api

import com.example.taskqueue.Instruction

interface ITask {

    // invoke on creation
    fun init() {}

    fun onTrigger(instruction: Instruction?) {

    }

    // invoke on destruction
    fun endTask() {

    }

}


