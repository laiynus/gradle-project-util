package com.epam.khrapavitski.gradle.util

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.codehaus.groovy.runtime.ResourceGroovyMethods

import com.epam.khrapavitski.gradle.util.pogo.WSDL

class UtilTask extends DefaultTask {

    def command

    @TaskAction
    def utilAction(){
        println "gradle-project-util: Running 'util $command'"
        project.logger.debug("gradle-project-util: Running 'util $command'")
        switch (command) {
            case ~/\b(S|s)ort(Properties|properties|Props|props)?\b/:
                sortProperties()
                break
            case ~/\b(D|d)ownload(Wsdl|wsdl|WSDL)?\b/:
                downloadWSDL()
                break
            case ~/\b(C|c)lean(Services|services)?\b/:
                cleanSidedServices()
                break
            default:
                println "gradle-project-util: Unkown command"
                break
        }
    }

    def sortProperties(){
        def file = project.file("${project.projectDir}/gradle.properties")
        if(!file){
            println "gradle-project-util: File gradle.properties is not found"
            project.logger.debug("gradle-project-util: File gradle.properties is not found")
        }else{
            def properties = new Properties()
            properties.load(file.newDataInputStream())
            if(properties.size() == 1 || properties.isEmpty()){
                println "gradle-project-util: Nothing to sort"
            }else{
                properties = properties.sort{a,b -> a.getKey() <=> b.getKey()}
                println "gradle-project-util: File gradle.properties sorted succsessfuly"
                println "gradle-project-util: Sorted list of properties:"
                properties.each{println it}
                file.withWriter {out -> properties.each {out.println it}}
            }
        }
    }

    def downloadWSDL(){
        def wsdls = project.gradleUtil.wsdls
        wsdls.each{WSDL wsdl ->
            def file = project.file("${project.projectDir}/services/${wsdl.getServiceName()}/src/main/resources/${wsdl.getFileName()}")
            if(!file.exists()){
                file.getParentFile().mkdirs()
                new File(file.getName()).createNewFile()
            }
            wsdl.getUrl().toURL().withInputStream {is ->
                file.withOutputStream {os ->
                    def bs = new BufferedOutputStream( os )
                    bs << is
                }
            }
            println "gradle-project-util: WSDL file from ${wsdl.getUrl()} succsessfuly downloaded"
        }
    }

    def cleanSidedServices(){
        def wsdls = project.gradleUtil.wsdls
        wsdls.each{WSDL wsdl ->
            def directory = project.file("${project.projectDir}/services/${wsdl.getServiceName()}/src/")
            if(directory.exists()){
                directory.deleteDir()
                println "gradle-project-util: Sided services succsessfuly deleted"
            }else{
                println "gradle-project-util: Sided services is not found"
                project.logger.debug("gradle-project-util: Sided services is not found")
            }
        }
    }
    
}
