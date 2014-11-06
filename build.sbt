name := """scalua"""

version := "0.0.1"

scalaVersion := "2.11.4"

libraryDependencies += Seq(
  "specs2" %% "specs2" % "2.4.9" % "test",
  "org.luaj" % "luaj-jse" % "3.0"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions += "-target:jvm-1.7"
