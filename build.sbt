name := """akka-persistence-playground"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.9",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.9",
  "com.github.dnvriend" %% "akka-persistence-inmemory" % "1.3.7",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.9" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test")

resolvers += Resolver.jcenterRepo
