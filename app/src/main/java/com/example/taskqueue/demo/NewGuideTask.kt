package com.example.taskqueue.demo

import com.example.taskqueue.api.ITask
import com.example.taskqueue.Instruction
import com.example.taskqueueannotation.annotation.Task

@Task(name = "new_guide")
class NewGuideTask: ITask {
    override fun init() {
        super.init()
    }

    override fun onTrigger(instruction: Instruction?) {
        super.onTrigger(instruction)
    }

    override fun endTask() {
        super.endTask()
    }
}