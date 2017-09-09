version := "1.0.6"
scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", "2.12.3")

name := "php-utils"
organization := "com.sandinh"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature")
scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 11)) => Seq("-target:jvm-1.8", "-Ybackend:GenBCode")
  case _ => Nil
})

parallelExecution in Test := false
