name := "monorail"
version := "0.1"
scalaVersion := "2.13.3"

scalafixScalaBinaryVersion := "2.13.3"

scalafmtOnCompile := true
scalafixOnCompile := true
coverageEnabled := true

addCompilerPlugin(scalafixSemanticdb)

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

  Seq(
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
