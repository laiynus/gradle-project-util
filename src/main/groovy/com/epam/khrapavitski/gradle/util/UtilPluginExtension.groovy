package com.epam.khrapavitski.gradle.util

import com.epam.khrapavitski.gradle.util.pogo.JavaClass;
import com.epam.khrapavitski.gradle.util.pogo.WSDL

class UtilPluginExtension {

    List<WSDL> wsdls = []

    List<JavaClass> javaClasses = []

    def wsdls(Closure closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = wsdls
        closure()
    }

    def wsdl(Closure closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        WSDL wsdl = new WSDL()
        closure.delegate = wsdl
        wsdls << wsdl
        closure()
    }

    def javaClasses(Closure closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = javaClasses
        closure()
    }

    def javaClass(Closure closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        JavaClass javaClass = new JavaClass()
        closure.delegate = javaClass
        javaClasses << javaClass
        closure()
    }
}
