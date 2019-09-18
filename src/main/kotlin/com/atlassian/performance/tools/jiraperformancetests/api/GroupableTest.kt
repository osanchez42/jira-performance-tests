package com.atlassian.performance.tools.jiraperformancetests.api

import com.atlassian.performance.tools.workspace.api.TaskWorkspace
import com.atlassian.performance.tools.workspace.api.TestWorkspace
import org.apache.logging.log4j.CloseableThreadContext

abstract class GroupableTest(
    val feature: String
) {
    fun run(
        group: String,
        workspace: TaskWorkspace
    ) {
        CloseableThreadContext.put("test", "$group : $feature").use {
            run(workspace.isolateTest("$group - $feature"))
        }
    }

    abstract fun run(workspace: TestWorkspace)
}