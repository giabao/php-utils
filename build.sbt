version := "1.0.8"
scalaVersion := "2.13.1"
crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")

name := "php-utils"
organization := "com.sandinh"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature")
scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 11)) => Seq("-target:jvm-1.8", "-Ybackend:GenBCode")
  case _ => Nil
})

parallelExecution in Test := false
