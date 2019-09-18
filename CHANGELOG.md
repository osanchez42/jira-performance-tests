# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## API
The API consists of all public Java types from `com.atlassian.performance.tools.jiraperformancetests.api` and its subpackages:

  * [source compatibility]
  * [binary compatibility]
  * [behavioral compatibility] with behavioral contracts expressed via Javadoc

[source compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#source_compatibility
[binary compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#binary_compatibility
[behavioral compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#behavioral_compatibility

## [Unreleased]
[Unreleased]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/master%0Drelease-3.4.0

## [3.4.0] - 2019-08-09
[3.4.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-3.4.0%0Drelease-3.3.0

### Added
- Add a public `feature` field to `GroupableTest`. Resolve [JPERF-550].

[JPERF-550]: https://ecosystem.atlassian.net/browse/JPERF-550

## [3.3.0] - 2019-05-02
[3.3.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-3.3.0%0Drelease-3.2.0

### Added
- Pass Jira admin credentials. Resolve [JPERF-451].

### Deprecated
- Deprecate `RegressionResults`, because it spreads the deprecated `CohortResult`.
- Deprecate `ProvisioningPerformanceTest` execution methods, which hardcode Jira admin credentials.

[JPERF-451]: https://ecosystem.atlassian.net/browse/JPERF-451

## [3.2.0] - 2019-02-28
[3.2.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-3.2.0%0Drelease-3.1.0

### Fixed
- Download Jira installer from the official downloads site. Resolve [JPERF-277].
- Use StackVirtualUsersFormula instead of Ec2VirtualUsersFormula. Work around [JPERF-406].

### Added
- Add support for `aws-infrastructure:2.6.0`.

[JPERF-277]: https://ecosystem.atlassian.net/browse/JPERF-277
[JPERF-406]: https://ecosystem.atlassian.net/browse/JPERF-406

## [3.1.0] - 2019-02-19
[3.1.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-3.1.0%0Drelease-3.0.0

### Added
- Expose failure and results in `ProvisioningPerformanceTest`. Resolve [JPERF-363] and [JPERF-391].

[JPERF-363]: https://ecosystem.atlassian.net/browse/JPERF-363
[JPERF-391]: https://ecosystem.atlassian.net/browse/JPERF-391

## [3.0.0] - 2019-01-22 🏅
[3.0.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-3.0.0%0Drelease-2.0.1

### Changed
- Depend on `jira-actions`. Progress towards [JPERF-356].

### Added
- Add support for `report:3`.
- Include `aws-infrastructure` in the API scope. Progress towards [JPERF-356].
- Include `aws-resources` in the API scope. Progress towards [JPERF-356].
- Include `virtual-users` in the API scope. Progress towards [JPERF-356].

### Removed
- Drop support for `report:2`.

[JPERF-356]: https://ecosystem.atlassian.net/browse/JPERF-356

## [2.0.1] - 2018-12-18 🎂
[2.0.1]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-2.0.1%0Drelease-2.0.0

### Fixed
- Timeline for `OnPremisePerformanceTest`. Resolve [JPERF-309].
- Enable diagnostics for `OnPremisePerformanceTest`. Resolve [JPERF-232].

[JPERF-232]: https://ecosystem.atlassian.net/browse/JPERF-232
[JPERF-309]: https://ecosystem.atlassian.net/browse/JPERF-309

## [2.0.0] - 2018-12-03
[2.0.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-2.0.0%0Drelease-1.3.0

### Added
- Allow JPT to instantiate different browsers which resolves [JPERF-169].
- Add support for `virtual-users:3.3.4`.
- Add support for `infrastructure:4`.
- Add support for `aws-infrastructure:2` which resolves [JPERF-280].

### Removed
- Drop deprecated classes `AppRegressionTest`,`AwsPluginTester` and `BtfJiraPerformanceMeter`.
- Drop support of `virtual-users:2`.
- Drop support of `infrastructure:2`.
- Drop support of `aws-infrastructure:1`.
- Remove Kotlin data-class generated methods from API.

[JPERF-169]: https://ecosystem.atlassian.net/browse/JPERF-169
[JPERF-280]: https://ecosystem.atlassian.net/browse/JPERF-280

## [1.3.0] - 2018-10-31
[1.3.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-1.3.0%0Drelease-1.2.1

### Added
- Expose `setAllowInsecureConnections` in `OnPremisePerformanceTest`. Work around [JPERF-196].

[JPERF-196]: https://ecosystem.atlassian.net/browse/JPERF-196

## [1.2.1] - 2018-10-16
[1.2.1]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-1.2.1%0Drelease-1.2.0

### Deprecated
- Deprecate the intermediate app test APIs. Promote the high-level API and low-level API instead.

## [1.2.0]
[1.2.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-1.2.0%0Drelease-1.1.0

### Added
- Add an option to test on premise Jira with custom test scenario, which resolves [JPERF-84].
- Accept any `AppSource` in `AppImpactTest`. Work around [JPERF-93].
- Support testing locally built apps. Work around [JPERF-93].

[JPERF-84]: https://ecosystem.atlassian.net/browse/JPERF-84
[JPERF-93]: https://ecosystem.atlassian.net/browse/JPERF-93

### Fixed
- Works around [JPERF-83].

[JPERF-83]: https://ecosystem.atlassian.net/browse/JPERF-83

## [1.1.0]
[1.1.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-1.1.0%0Drelease-1.0.0

### Added
- Add a way to test on premise Jira instance which resolves [JPERF-16](https://ecosystem.atlassian.net/browse/JPERF-16)

## [1.0.0]
[1.0.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-1.0.0%0Drelease-0.1.2

## [0.1.2]
[0.1.2]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-0.1.2%0Drelease-0.1.1

### Changed
- Use stable APIs.

### Added
- Added `AwsHousekeeping` to the API.

## [0.1.1]
[0.1.1]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-0.1.1%0Drelease-0.1.0

### Added
- Allow throttling virtual user diagnostics. 

### Changed
- Define the public API.

## [0.1.0]
[0.1.0]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-0.1.0%0Drelease-0.0.3

### Changed
- Reshape `JiraPerformanceTest` into `AppImpactTest`.

### Added
- Choose deployment for Jira and number of nodes for DC.

### Fixed
- Expect a correct report count.
- Force updating snapshots when running `testRefApp`.
- Hint how to customize the `AppImpactTest`.
- Depend on a stable version of APT `infrastructure`.
- Depend on a stable version of APT `report`.

## [0.0.3]
[0.0.3]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-0.0.3%0Drelease-0.0.2

### Added
- Allow running performance tests without extra Jira apps installed.
- Generate charts in app tests. [JPERF-9](https://ecosystem.atlassian.net/browse/JPERF-9).
- Explain [contribution guidelines](CONTRIBUTING.md).

### Fixed
- Correctly label the experiment test cohort.
- Distinguish between cohorts even if we test the same version of a plugin. [JPERF-7](https://ecosystem.atlassian.net/browse/JPERF-7).
- Allow tests to consume custom datasets created within the same task.
- Fix scanner errors in log. See [JPERF-10](https://ecosystem.atlassian.net/browse/JPERF-10).
- Print a full stack trace when ref app fails. [JPERF-8](https://ecosystem.atlassian.net/browse/JPERF-8).

## [0.0.2] - 2018-08-08
[0.0.2]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-0.0.2%0Drelease-0.0.1

### Fixed
- Add the missing virtual users main class. See [JPERF-2](https://ecosystem.atlassian.net/browse/JPERF-2).

## [0.0.1] - 2018-08-07
[0.0.1]: https://bitbucket.org/atlassian/jira-performance-tests/branches/compare/release-0.0.1%0Dinitial-commit

### Added
- Migrate high-level test API from [JPT submodule].
- Add [README.md](README.md).
- Add this changelog.
- Enable Bitbucket Pipelines.

[JPT submodule]: https://stash.atlassian.com/projects/JIRASERVER/repos/jira-performance-tests/browse/jira-performance-tests?at=24b1522734605e8689a72396917e6080fddb8731
