package com.example.taskqueueprocessor.processor

import com.example.taskqueueannotation.annotation.ITaskRegister
import com.example.taskqueueannotation.annotation.Task
import com.example.taskqueueannotation.annotation.TaskInterceptor
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class TaskQueueProcessor : AbstractProcessor() {
    private var mFileUtils: Filer? = null
    private var mElementUtils: Elements? = null
    private var mHasProcess = false

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        mFileUtils = p0?.filer
        mElementUtils = p0?.elementUtils
        Logger.init(p0?.messager)
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        if (mHasProcess) {
            return false
        }
        mHasProcess = true
        val elements = p1?.getElementsAnnotatedWith(Task::class.java)
        val taskPathMap = mutableMapOf<String, String>()
        elements?.forEach {
            val typeElement = it as TypeElement
            val task = typeElement.getAnnotation(Task::class.java)
            taskPathMap[task.name] = typeElement.qualifiedName.toString()
        }
        generateRegister(taskPathMap)
        Logger.info("调用了")
        return true
    }

    private fun getPkgName(type: TypeElement) =
        mElementUtils?.getPackageOf(type)?.qualifiedName.toString()

    private fun FileSpec.writeFile() {

        val kaptKotlinGenerateDir = processingEnv.options["kapt.kotlin.generated"]
        kaptKotlinGenerateDir?.takeIf {
            it.isNotEmpty()
        }?.let {
            File(it)
        }?.also {
            it.mkdirs()
            writeTo(it.toPath())
        }
    }


    private fun generateRegister(taskPathMap: Map<String, String>) {
        val taskPathMapGetterBuilder = FunSpec.getterBuilder()
            .addStatement("val map = mutableMapOf<String, String>()")
        taskPathMap.forEach { (s, s2) ->
            taskPathMapGetterBuilder.addStatement("map[%S] = %S", s, s2)
        }
        taskPathMapGetterBuilder.addStatement("return map")
        val file = FileSpec.builder(
            "", "TaskRegister_gen"
        )
            .addType(
                TypeSpec.classBuilder("TaskRegister_gen")
                    .addSuperinterface(ITaskRegister::class)
                    .addProperty(
                        PropertySpec.builder(
                            "taskPathMap", Map::class.asClassName().parameterizedBy(
                                String::class.asClassName(), String::class.asClassName()
                            )
                        )
                            .addModifiers(KModifier.OVERRIDE)
                            .getter(taskPathMapGetterBuilder.build())
                            .build()
                    )
                    .build()
            )
            .build()
        file.writeFile()

    }

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(
        Task::class.java.canonicalName,
        TaskInterceptor::class.java.canonicalName
    )
}