version := "1.0.4-SNAPSHOT"
scalaVersion := "2.11.7"
crossScalaVersions := Seq(scalaVersion.value, "2.10.5")

name := "php-utils"
organization := "com.sandinh"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % Test

scalacOptions ++= Seq("-encoding", "UTF-8", "-feature", "-deprecation")

parallelExecution in Test := false
