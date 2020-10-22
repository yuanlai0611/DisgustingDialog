package com.example.taskqueue

import com.example.taskqueue.api.ITask
import com.example.taskqueue.task.BaseTaskImpl
import com.example.taskqueue.task.EndTask
import com.example.taskqueue.task.WorkTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

// mutual exclusion of tasks
object TaskQueue {

    // save the kept task(annotated with keep = true)
    private val keepTaskMap = mutableMapOf<String, ITask>()
    private val mWorkQueue = Channel<WorkTask>()
    private val mEndQueue = Channel<EndTask>()

    // end task queue
    // instruction to drive the task
    // first in the main thread to avoid concurrency problem
    private val mScope = CoroutineScope(Dispatchers.Main)
    private var mRunningTask: BaseTaskImpl? = null

    init {
        initTaskQueue()
    }

    // taskQueue start infinite loop
    // only when the work task meets the corresponding end task will it continue to execute the queue
    private fun initTaskQueue() = mScope.launch {
        for (workTask in mWorkQueue) {
            workTask.init()
            workTask.onTrigger(workTask.instruction)
            mRunningTask = workTask
            for (endTask in mEndQueue) {
                if (workTask.taskImpl == endTask.taskImpl) {
                    endTask.endTask()
                    break
                }
            }
        }
    }


    private fun enqueueTask(task: ITask) = mScope.launch {
        mWorkQueue.offer(WorkTask(task))
    }

    // 标记
    fun finishTask(task: ITask?) = task?.let {
        mScope.launch {
            mEndQueue.offer(EndTask(task))
        }
    }

    object Builder {
        private lateinit var mInstruction: Instruction

        fun newBuilder(taskName: String): Builder {
            mInstruction = Instruction()
            mInstruction.taskName = taskName
            return this
        }

        fun withInt(key: String, value: Int): Builder {
            mInstruction.putInt(key, value)
            return this
        }

        fun withFloat(key: String, value: Float): Builder {
            mInstruction.putFloat(key, value)
            return this
        }

        fun withLong(key: String, value: Long): Builder {
            mInstruction.putLong(key, value)
            return this
        }

        fun withDouble(key: String, value: Double): Builder {
            mInstruction.putDouble(key, value)
            return this
        }

        fun <T : Any> withObject(key: String, value: T): Builder {
            mInstruction.putObject(key, value)
            return this
        }

        fun builder(): Instruction {
            return mInstruction
        }
    }

}