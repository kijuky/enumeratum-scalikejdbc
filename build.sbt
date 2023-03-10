lazy val scalikejdbc4 = (project in file("scalikejdbc4"))
  .settings(
    name := "enumeratum-scalikejdbc4",
    scalaVersion := "2.13.10",
    crossScalaVersions := Seq("3.2.2", "2.13.10", "2.12.17"),
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, _)) =>
          Seq("-Yretain-trees")
        case Some((2, 13)) =>
          Seq("-deprecation", "-Xlint", "-Werror")
        case _ =>
          Seq("-Xlint")
      }
    },
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % "1.7.2",
      "org.scalikejdbc" %% "scalikejdbc" % "4.0.0" % Provided
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.1.214",
      "org.scalatest" %% "scalatest" % "3.2.15", // scala-steward:off
      "org.scalikejdbc" %% "scalikejdbc-test" % "4.0.0",
      "org.slf4j" % "slf4j-nop" % "1.7.32" // scala-steward:off
    ).map(_ % Test),
    Test / parallelExecution := false
  )

lazy val scalikejdbc35 = (project in file("scalikejdbc35"))
  .settings(
    name := "enumeratum-scalikejdbc35",
    scalaVersion := "2.13.10",
    crossScalaVersions := Seq("2.13.10", "2.12.17"),
    scalacOptions ++= Seq("-deprecation"),
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % "1.7.2",
      "org.scalikejdbc" %% "scalikejdbc" % "3.5.0" % Provided // scala-steward:off
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.1.214",
      "org.scalatest" %% "scalatest" % "3.2.15", // scala-steward:off
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.5.0", // scala-steward:off
      "org.slf4j" % "slf4j-nop" % "1.7.30" // scala-steward:off
    ).map(_ % Test),
    Test / parallelExecution := false
  )

lazy val scalikejdbc34 = (project in file("scalikejdbc34"))
  .settings(
    name := "enumeratum-scalikejdbc34",
    scalaVersion := "2.13.10",
    crossScalaVersions := Seq("2.13.10", "2.12.17", "2.11.12"),
    scalacOptions ++= Seq("-deprecation"),
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % "1.7.2",
      "org.scalikejdbc" %% "scalikejdbc" % "3.4.2" % Provided // scala-steward:off
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.1.214",
      "org.scalatest" %% "scalatest" % "3.0.9", // scala-steward:off
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.4.2", // scala-steward:off
      "org.slf4j" % "slf4j-nop" % "1.7.30" // scala-steward:off
    ).map(_ % Test),
    Test / parallelExecution := false
  )

inThisBuild(
  Seq(
    organization := "io.github.kijuky",
    homepage := Some(url("https://github.com/kijuky/enumeratum-scalikejdbc")),
    licenses := Seq(
      "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
      Developer(
        "kijuky",
        "Kizuki YASUE",
        "ikuzik@gmail.com",
        url("https://github.com/kijuky")
      )
    ),
    versionScheme := Some("early-semver"),
    sonatypeCredentialHost := "s01.oss.sonatype.org",
    sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
  )
)

lazy val root = (project in file(".") withId "enumeratum-scalikejdbc")
  .settings(publish / skip := true)
  .aggregate(scalikejdbc4, scalikejdbc35, scalikejdbc34)
