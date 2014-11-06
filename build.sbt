name := """scalua"""

version := "0.0.1"

scalaVersion := "2.11.4"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  "org.luaj" % "luaj-jse" % "3.0"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-target:jvm-1.7", "-feature")
