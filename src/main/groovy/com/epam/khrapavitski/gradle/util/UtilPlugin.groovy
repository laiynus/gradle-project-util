package com.epam.khrapavitski.gradle.util

import org.gradle.api.Plugin
import org.gradle.api.Project

import com.epam.khrapavitski.gradle.util.UtilPluginExtension

class UtilPlugin implements Plugin<Project>{

    static final String GRADLE_UTIL_EXTENSION_NAME = "gradleUtil"
    
    void apply(Project project) {
        applyTasks(project)
        applyExtension(project)
    }
    
    void applyTasks(Project project) {
        [
            'sortProperties': 'Sort file gradle.properties',
            'downloadWSDL': 'Download wsdl file from outside webservice',
            'cleanServices': 'Clean java classes and wslds file of sided webservices',
            'wsdl2Java': 'Genenate java source code from WSDL-file',
            'java2WSDL': 'Genenate WSDL files from java source code'
        ].each { taskName, taskDescription ->
            def commandName = taskName
            project.task(taskName, type: UtilTask) {
                description = taskDescription
                command = commandName
                group = "util"
            }
        }
    }

    void applyExtension(Project project) {
        project.extensions.create(GRADLE_UTIL_EXTENSION_NAME, UtilPluginExtension)
    }
    
}