import java.time.LocalDate

import com.typesafe.sbt.packager.docker.{Cmd, ExecCmd}
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

lazy val changelogTemplatePath    = settingKey[String]("Path to CHANGELOG.md template")
lazy val changelogDestinationPath = settingKey[String]("Path to CHANGELOG.md destination")
lazy val changelogGenerate        = taskKey[Unit]("Generates CHANGELOG.md file based on git log")

name := "monorail"
scalaVersion := "2.13.3"

scalafixScalaBinaryVersion := "2.13.3"

scalafmtOnCompile := true
scalafixOnCompile := true

dockerBaseImage := "adoptopenjdk/openjdk14:jre-14.0.2_12-alpine"
dockerExposedPorts := List(8080)
dockerRepository := Some("docker.pkg.github.com")
dockerUsername := Some("melalex")
dockerAlias := DockerAlias(dockerRepository.value, dockerUsername.value, s"${name.value}/monorail-api", Some(version.value))
dockerUpdateLatest := true
dockerCommands ++= List(Cmd("USER", "root"), ExecCmd("RUN", "apk", "add", "--no-cache", "bash"))

releaseCommitMessage := s"[skip ci] set version to ${(version in ThisBuild).value}"

majorRegexes := List(ChangeLogger.BreakingChangeRegEx)
minorRegexes := List(ChangeLogger.FeatureRegEx)
bugfixRegexes := List(ChangeLogger.FixRegEx, ChangeLogger.RefactoringRegEx)

changelogTemplatePath := "project/CHANGELOG.md.ssp"
changelogDestinationPath := "target/changelog/CHANGELOG.md"

addCompilerPlugin(scalafixSemanticdb)
enablePlugins(JavaAppPackaging, DockerPlugin)

libraryDependencies ++= {

  // Dependencies versions
  val akkaHttpVersion   = "10.2.0"
  val akkaVersion       = "2.6.8"
  val akkaJsonVersion   = "1.34.0"
  val macWireVersion    = "2.3.7"
  val circeVersion      = "0.13.0"
  val logbackVersion    = "1.2.3"
  val pureConfigVersion = "0.13.0"
  val scalaTestVersion  = "3.2.1"
  val scalaMockVersion  = "5.0.0"

  List(
    // Akka
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream"      % akkaVersion,
    "com.typesafe.akka" %% "akka-pki"         % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"       % akkaVersion,
    "com.typesafe.akka" %% "akka-discovery"   % akkaVersion,
    "com.typesafe.akka" %% "akka-http"        % akkaHttpVersion,
    // DI
    "com.softwaremill.macwire" %% "macros" % macWireVersion,
    // Json Ser/Der
    "io.circe"          %% "circe-core"      % circeVersion,
    "io.circe"          %% "circe-generic"   % circeVersion,
    "io.circe"          %% "circe-parser"    % circeVersion,
    "de.heikoseeberger" %% "akka-http-circe" % akkaJsonVersion,
    // Other
    "ch.qos.logback"        % "logback-classic" % logbackVersion,
    "com.github.pureconfig" %% "pureconfig"     % pureConfigVersion,
    // Test
    "org.scalatest"     %% "scalatest"                % scalaTestVersion % Test,
    "org.scalamock"     %% "scalamock"                % scalaMockVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion  % Test,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion      % Test,
    "com.typesafe.akka" %% "akka-stream-testkit"      % akkaVersion      % Test
  )
}

releaseProcess := List[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  ReleaseStep(releaseStepTask(changelogGenerate))
//  ,
//  commitReleaseVersion,
//  tagRelease,
//  ReleaseStep(releaseStepTask(publish in Docker)),
//  setNextVersion,
//  commitNextVersion,
//  pushChanges
)

changelogGenerate := {
  val changelog = ChangeLogger.generateChangelogString(
    changelogTemplatePath.value,
    version.value,
    LocalDate.now(),
    unreleasedCommits.value.map(_.msg)
  )

  print(changelog)

  IO.write(new File(changelogDestinationPath.value), changelog)
}
