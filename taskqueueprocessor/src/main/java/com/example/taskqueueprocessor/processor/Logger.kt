package com.example.taskqueueprocessor.processor

import javax.annotation.processing.Messager
import javax.tools.Diagnostic

object Logger {

    private const val TAG = "TaskQueueProcessor"
    private var mMessager: Messager? = null

    fun init(messager: Messager?) {
        mMessager = messager
    }

    private fun print(kind: Diagnostic.Kind, info: CharSequence?) {
        info?.takeIf {
            it.isNotEmpty()
        }?.also {
            mMessager?.printMessage(kind, it)
        }
    }

    fun info(info: CharSequence?) {
        print(Diagnostic.Kind.NOTE, "$TAG[INFO] $info\n")
    }

    fun waring(warning: CharSequence?) {
        print(Diagnostic.Kind.WARNING, "$TAG[WARNING] $warning\n")
    }

    fun error(error: CharSequence?) {
        print(Diagnostic.Kind.ERROR, "$TAG[ERROR] $error\n")
    }

    fun error(throwable: Throwable) {
        print(Diagnostic.Kind.ERROR, "$TAG[ERROR] ${throwable.stackTrace}\n")
    }

}
