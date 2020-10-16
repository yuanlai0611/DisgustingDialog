package com.example.taskqueue.processor

import com.example.taskqueue.annotation.Task
import com.example.taskqueue.annotation.TaskInterceptor
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

@AutoService(Processor::class)
class TaskQueueProcessor: AbstractProcessor() {
    private var mFileUtils: Filer? = null
    private var mElementUtils: Elements? = null
    private var mMessager: Messager? = null

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        mFileUtils = p0?.filer
        mElementUtils = p0?.elementUtils
        mMessager = p0?.messager
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        val taskElements = p1?.getElementsAnnotatedWith(Task::class.java)
        taskElements?.forEach {
            val variableElement = it as VariableElement
            val typeElement = variableElement.enclosingElement as TypeElement
            val qualifiedName = typeElement.qualifiedName?.toString()

            mMessager?.printMessage(Diagnostic.Kind.OTHER, "qualifiedName: $qualifiedName")
        }
        generateTestClass()
        return true
    }

    private fun generateTestClass() {
        val greeterClass = ClassName("", "Greeter")
        val file = FileSpec.builder("", "HelloWorld") //指定生成的文件名：'HelloWorld.kt'
                //在'HelloWorld.kt'中生成类：'Greeter'
                .addType(TypeSpec.classBuilder("Greeter")
                        //在类Greeter添加一个主构造函数
                        .primaryConstructor(FunSpec.constructorBuilder()
                                //主构造函数的参数为一个名为name的String对象
                                .addParameter("name", String::class)
                                .build())
                        //在类Greeter生成成员变量：name: String
                        .addProperty(PropertySpec.builder("name", String::class)
                                .initializer("name")
                                .build())
                        //类Greeter生成无参无返回值函数：greet
                        .addFunction(FunSpec.builder("greet")
                                //greet的函数的函数体
                                .addStatement("println(%P)", "Hello, \$name")
                                .build())
                        .build())
                //在'HelloWorld.kt'中生成无返回值函数：'main'
                .addFunction(FunSpec.builder("main")
                        // 'main'函数的参数
                        .addParameter("args", String::class, KModifier.VARARG)
                        // 'main'函数的函数体
                        .addStatement("%T(args[0]).greet()", greeterClass)
                        .build())
                .build()

        file.writeTo(System.out)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String?> = mutableSetOf(
            Task::class.qualifiedName,
            TaskInterceptor::class.qualifiedName
    )
}