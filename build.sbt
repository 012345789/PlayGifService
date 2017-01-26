name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += jdbc
libraryDependencies += cache
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test

// Target Java 8 as Debian defaults to 9, which is unstable
scalacOptions += "-target:jvm-1.8"

// DEBIAN SPECIFIC
debianPackageDependencies in Debian ++= Seq("openjdk-8-jdk", "bash (>= 3.2)")
