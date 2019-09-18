package com.atlassian.performance.tools.jiraperformancetests.api

import com.atlassian.performance.tools.aws.api.Aws
import com.atlassian.performance.tools.aws.api.Investment
import com.atlassian.performance.tools.awsinfrastructure.api.DatasetCatalogue
import com.atlassian.performance.tools.awsinfrastructure.api.InfrastructureFormula
import com.atlassian.performance.tools.awsinfrastructure.api.TargetingVirtualUserOptions
import com.atlassian.performance.tools.awsinfrastructure.api.jira.StandaloneFormula
import com.atlassian.performance.tools.awsinfrastructure.api.virtualusers.StackVirtualUsersFormula
import com.atlassian.performance.tools.infrastructure.api.app.AppSource
import com.atlassian.performance.tools.infrastructure.api.app.Apps
import com.atlassian.performance.tools.infrastructure.api.app.MavenApp
import com.atlassian.performance.tools.infrastructure.api.app.NoApp
import com.atlassian.performance.tools.infrastructure.api.dataset.Dataset
import com.atlassian.performance.tools.infrastructure.api.distribution.PublicJiraSoftwareDistribution
import com.atlassian.performance.tools.jiraactions.api.ActionType
import com.atlassian.performance.tools.jiraactions.api.scenario.Scenario
import com.atlassian.performance.tools.jiraperformancetests.CountingThreadFactory
import com.atlassian.performance.tools.jiraperformancetests.RawRegressionResults
import com.atlassian.performance.tools.jirasoftwareactions.api.JiraSoftwareScenario
import com.atlassian.performance.tools.report.api.Criteria
import com.atlassian.performance.tools.report.api.PerformanceCriteria
import com.atlassian.performance.tools.report.api.judge.MaximumCoverageJudge
import com.atlassian.performance.tools.virtualusers.api.VirtualUserLoad
import com.atlassian.performance.tools.virtualusers.api.VirtualUserOptions
import com.atlassian.performance.tools.virtualusers.api.browsers.Browser
import com.atlassian.performance.tools.virtualusers.api.browsers.HeadlessChromeBrowser
import com.atlassian.performance.tools.virtualusers.api.config.VirtualUserBehavior
import com.atlassian.performance.tools.virtualusers.api.config.VirtualUserTarget
import com.atlassian.performance.tools.workspace.api.RootWorkspace
import com.atlassian.performance.tools.workspace.api.TestWorkspace
import java.io.File
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration
import java.util.concurrent.Executors

/**
 * Tests the performance impact of the [app].
 */
class AppImpactTest(
    private val app: AppSource,
    private val aws: Aws,
    var testJar: File
) {

    constructor(
        app: MavenApp,
        aws: Aws
    ) : this(
        app = app,
        aws = aws,
        testJar = File("target/${app.artifactId}-performance-tests-${app.version}-fat-tests.jar")
    )

    var scenario: Class<out Scenario> = JiraSoftwareScenario::class.java
    var browser: Class<out Browser> = HeadlessChromeBrowser::class.java
    var criteria: Map<ActionType<*>, Criteria> = emptyMap()
    var jiraVersion: String = "7.5.0"
    var duration: Duration = Duration.ofMinutes(20)
    internal var dataset: Dataset = DatasetCatalogue().largeJiraSeven()
    private val outputDirectory: Path = Paths.get("target")
    private val appLabel = app.getLabel()

    fun run() {
        val load = VirtualUserLoad.Builder()
            .virtualUsers(10)
            .hold(Duration.ZERO)
            .ramp(Duration.ZERO)
            .flat(duration)
            .build()
        val results = runRegression(load)
        assertNoRegression(results, load)
    }

    private fun runRegression(
        load: VirtualUserLoad
    ): RawRegressionResults {
        val workspace = RootWorkspace(outputDirectory).currentTask.isolateTest("App impact test")
        val baseline = testCohort(
            cohort = "without $appLabel",
            app = NoApp()
        )
        val experiment = testCohort(
            cohort = "with $appLabel",
            app = app
        )
        val virtualUserBehavior = VirtualUserBehavior.Builder(scenario)
            .load(load)
            .browser(browser)
            .diagnosticsLimit(255)
            .seed(123)
            .build()
        val virtualUserOptions = object : TargetingVirtualUserOptions {
            override fun target(
                jira: URI
            ): VirtualUserOptions = VirtualUserOptions(
                target = VirtualUserTarget(
                    jira,
                    userName = "admin",
                    password = "admin"
                ),
                behavior = virtualUserBehavior
            )
        }
        val executor = Executors.newFixedThreadPool(2, CountingThreadFactory("standalone-stability-test"))
        val futureBaselineResults = baseline.executeAsync(workspace, executor, virtualUserOptions)
        val futureExperimentResults = experiment.executeAsync(workspace, executor, virtualUserOptions)
        val baselineResults = futureBaselineResults.get()
        val experimentResults = futureExperimentResults.get()
        executor.shutdownNow()
        return RawRegressionResults(
            baseline = baselineResults,
            experiment = experimentResults
        )
    }

    private fun testCohort(
        cohort: String,
        app: AppSource
    ): ProvisioningPerformanceTest = ProvisioningPerformanceTest(
        cohort = cohort,
        infrastructureFormula = InfrastructureFormula(
            investment = Investment(
                useCase = "Measure app impact of $appLabel",
                lifespan = Duration.ofHours(1)
            ),
            jiraFormula = StandaloneFormula.Builder(
                productDistribution = PublicJiraSoftwareDistribution(jiraVersion),
                jiraHomeSource = dataset.jiraHomeSource,
                database = dataset.database
            )
                .apps(Apps(listOf(app)))
                .build(),
            virtualUsersFormula = StackVirtualUsersFormula.Builder(testJar).build(),
            aws = aws
        )
    )

    private fun assertNoRegression(
        results: RawRegressionResults,
        load: VirtualUserLoad
    ) {
        val reportWorkspace = outputDirectory.resolve("surefire-reports")
        val verdict = MaximumCoverageJudge().judge(
            baseline = results.baseline,
            experiment = results.experiment,
            criteria = PerformanceCriteria(
                actionCriteria = criteria,
                virtualUserLoad = load
            ),
            report = TestWorkspace(reportWorkspace)
        )
        verdict.assertAccepted(
            javaClass.canonicalName,
            reportWorkspace
        )
    }
}
