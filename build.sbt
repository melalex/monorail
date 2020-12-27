import java.nio.charset.StandardCharsets
import java.time.LocalDate

import com.typesafe.sbt.packager.docker.{Cmd, ExecCmd}
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

import scala.reflect.io.Path

lazy val changelogTemplatePath    = settingKey[Path]("Path to CHANGELOG.md template")
lazy val changelogDestinationPath = settingKey[Path]("Path to CHANGELOG.md destination")
lazy val changelogGenerate        = taskKey[Unit]("Generates CHANGELOG.md file based on git log")

name := "monorail"
scalaVersion := "2.13.3"

scalafixScalaBinaryVersion := "2.13.3"

scalafmtOnCompile := false // fails on CI
scalafixOnCompile := false // fails on CI

dockerBaseImage := "adoptopenjdk/openjdk14:jre-14.0.2_12-alpine"
dockerExposedVolumes := Seq("/var/monorail/conf", "/var/monorail/logs")
dockerExposedPorts := Seq(8080)
dockerRepository := Some("docker.pkg.github.com")
dockerUsername := Some("melalex")
dockerAlias := DockerAlias(dockerRepository.value, dockerUsername.value, s"${name.value}/monorail-api", Some(version.value))
dockerUpdateLatest := true
dockerCommands ++= List(Cmd("USER", "root"), ExecCmd("RUN", "apk", "add", "--no-cache", "bash"))

releaseCommitMessage := s"[skip ci] set version to ${(version in ThisBuild).value}"

majorRegexes := List(ChangeLogger.BreakingChangeRegEx)
minorRegexes := List(ChangeLogger.FeatureRegEx)
bugfixRegexes := List(ChangeLogger.FixRegEx, ChangeLogger.RefactoringRegEx)

changelogTemplatePath := Path("project/CHANGELOG.md.ssp")
changelogDestinationPath := Path("target/changelog/CHANGELOG.md")

addCompilerPlugin(scalafixSemanticdb)
enablePlugins(JavaAppPackaging, DockerPlugin)

scalacOptions ++= List(
  "-Xfatal-warnings",
  "-Ymacro-annotations",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)

libraryDependencies ++= {
  val akkaHttpVersion        = "10.2.0"
  val akkaVersion            = "2.6.8"
  val akkaSessionVersion     = "0.5.11"
  val akkaJsonVersion        = "1.34.0"
  val macWireVersion         = "2.3.7"
  val circeVersion           = "0.13.0"
  val logbackVersion         = "1.2.3"
  val googleApiClientVersion = "1.31.1"
  val fireStoreClientVersion = "2.1.0"
  val pureConfigVersion      = "0.13.0"
  val scalaTestVersion       = "3.2.1"
  val scalaMockVersion       = "5.0.0"
  val chimneyVersion         = "0.6.1"

  val akkaDependencies = List(
    "com.typesafe.akka"                  %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka"                  %% "akka-stream"      % akkaVersion,
    "com.typesafe.akka"                  %% "akka-pki"         % akkaVersion,
    "com.typesafe.akka"                  %% "akka-slf4j"       % akkaVersion,
    "com.typesafe.akka"                  %% "akka-discovery"   % akkaVersion,
    "com.typesafe.akka"                  %% "akka-http"        % akkaHttpVersion,
    "com.softwaremill.akka-http-session" %% "core"             % akkaSessionVersion,
    "com.softwaremill.akka-http-session" %% "jwt"              % akkaSessionVersion
  )

  val jsonDependencies = List(
    "io.circe"          %% "circe-core"           % circeVersion,
    "io.circe"          %% "circe-generic"        % circeVersion,
    "io.circe"          %% "circe-parser"         % circeVersion,
    "io.circe"          %% "circe-generic-extras" % circeVersion,
    "io.circe"          %% "circe-jackson28"      % circeVersion,
    "de.heikoseeberger" %% "akka-http-circe"      % akkaJsonVersion
  )

  val googleDependencies = List(
    "com.google.api-client" % "google-api-client"      % googleApiClientVersion,
    "com.google.cloud"      % "google-cloud-firestore" % fireStoreClientVersion
  )

  val utilDependencies = List(
    "ch.qos.logback"           % "logback-classic" % logbackVersion,
    "com.softwaremill.macwire" %% "macros"         % macWireVersion,
    "com.github.pureconfig"    %% "pureconfig"     % pureConfigVersion,
    "io.scalaland"             %% "chimney"        % chimneyVersion
  )

  val testDependencies = List(
    "org.scalatest"     %% "scalatest"                % scalaTestVersion % Test,
    "org.scalamock"     %% "scalamock"                % scalaMockVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion  % Test,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion      % Test,
    "com.typesafe.akka" %% "akka-stream-testkit"      % akkaVersion      % Test
  )

  akkaDependencies ::: jsonDependencies ::: googleDependencies ::: utilDependencies ::: testDependencies
}

releaseProcess := List[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  ReleaseStep(releaseStepTask(changelogGenerate)),
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(releaseStepTask(publish in Docker)),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

changelogGenerate := {
  val changelog = ChangeLogger.generateChangelogString(
    changelogTemplatePath.value,
    version.value,
    LocalDate.now(),
    unreleasedCommits.value.map(_.msg)
  )

  IO.write(changelogDestinationPath.value.jfile, changelog.getBytes(StandardCharsets.UTF_8))
}
