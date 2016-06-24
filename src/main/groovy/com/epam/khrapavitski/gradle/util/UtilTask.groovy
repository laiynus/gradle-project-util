package com.epam.khrapavitski.gradle.util

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

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
    
}
