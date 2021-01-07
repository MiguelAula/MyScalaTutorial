name := "scalaTeaching"   //project name

version := "0.1"          //project version

scalaVersion := "2.13.4"  //scala version

libraryDependencies += "org.scalameta" %% "munit" % "0.7.19" % Test
testFrameworks += new TestFramework("munit.Framework")

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.3.0"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1"
