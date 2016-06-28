package com.epam.khrapavitski.gradle.util

import com.epam.khrapavitski.gradle.util.pogo.WSDL

class UtilPluginExtension {
    
    List<WSDL> wsdls = []
    
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
    
}