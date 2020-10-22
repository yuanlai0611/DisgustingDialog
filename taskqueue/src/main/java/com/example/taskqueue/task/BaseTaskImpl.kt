package com.example.taskqueue.task

import com.example.taskqueue.Instruction
import com.example.taskqueue.api.ITask

internal open class BaseTaskImpl(val taskImpl: ITask): ITask by taskImpl {

    lateinit var taskName: String
    lateinit var instruction: Instruction
    protected var keep: Boolean = false


}