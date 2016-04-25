version := "1.0.5-SNAPSHOT"
scalaVersion := "2.11.8"
crossScalaVersions := Seq("2.11.8", "2.12.0-M4")

name := "php-utils"
organization := "com.sandinh"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature")
scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 11)) => Seq("-target:jvm-1.8", "-Ybackend:GenBCode")
  case _ => Nil
})

parallelExecution in Test := false
