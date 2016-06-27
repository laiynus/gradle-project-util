package com.epam.khrapavitski.gradle.util

import org.gradle.api.Plugin
import org.gradle.api.Project

class UtilPlugin implements Plugin<Project>{
    
    void apply(Project project) {
        applyTasks(project)
    }
    
    void applyTasks(Project project) {
        [
            'sortProperties': 'Sort file gradle.properties',
            '':''
        ].each { taskName, taskDescription ->
            def commandName = taskName
            project.task(taskName, type: UtilTask) {
                description = taskDescription
                command = commandName
                group = "util"
            }
        }
    }
    
}