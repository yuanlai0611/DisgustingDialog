package com.example.taskqueue.task

import com.example.taskqueue.api.ITask

// concrete task running in the task queue
// can not be access by the outside
internal class WorkTask(taskImpl: ITask): BaseTaskImpl(taskImpl) {





}