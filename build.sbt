name := "monorail"
version := "0.1"
scalaVersion := "2.13.3"

// Dependencies versions
val akkaHttpVersion = "10.1.12"
val akkaVersion = "2.6.8"
val scalaTestVersion = "3.2.0"

enablePlugins(AkkaGrpcPlugin)

libraryDependencies ++= Seq(
  // Akka
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-pki" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  // Test
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test
)
