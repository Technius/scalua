publishMavenStyle := true

pomIncludeRepository := { _ => false }

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <url>http://technius.github.io/scalua</url>
  <licenses>
    <license>
      <name>The Mit License</name>
      <url>http://opensource.org/licenses/MIT</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:Technius/scalua.git</url>
    <connection>scm:git:git@github.com:Technius/scalua.git</connection>
  </scm>
  <developers>
    <developer>
      <id>technius</id>
      <name>Bryan Tan</name>
      <url>http://technius.co</url>
    </developer>
  </developers>)

(sys.env.get("SONATYPE_USERNAME"), sys.env.get("SONATYPE_PASSWORD")) match {
  case (Some(username), Some(password)) =>
    credentials += Credentials(
      "Sonatype Nexus Repository Manager", 
      "oss.sonatype.org", 
      username, 
      password
    )
  case _ => credentials ++= Seq()
}
