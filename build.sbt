lazy val scalikejdbc43 = project
  .in(file("scalikejdbc43"))
  .settings(
    name := "enumeratum-scalikejdbc43",
    scalaVersion := "3.3.3",
    crossScalaVersions := Seq("3.3.3", "2.13.14", "2.12.19"),
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
      "com.beachape" %% "enumeratum" % "1.7.4",
      "org.scalikejdbc" %% "scalikejdbc" % "4.3.0" % Provided
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.2.224",
      "org.scalatest" %% "scalatest" % "3.2.18", // scala-steward:off
      "org.scalikejdbc" %% "scalikejdbc-test" % "4.3.0",
      "org.slf4j" % "slf4j-nop" % "2.0.13" // scala-steward:off
    ).map(_ % Test),
    Test / parallelExecution := false
  )

lazy val scalikejdbc4 = project
  .in(file("scalikejdbc4"))
  .settings(
    name := "enumeratum-scalikejdbc4",
    scalaVersion := "2.13.14",
    crossScalaVersions := Seq("3.3.3", "2.13.14", "2.12.19"),
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
      "com.beachape" %% "enumeratum" % "1.7.4",
      "org.scalikejdbc" %% "scalikejdbc" % "4.0.0" % Provided // scala-steward:off
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.2.224",
      "org.scalatest" %% "scalatest" % "3.2.15", // scala-steward:off
      "org.scalikejdbc" %% "scalikejdbc-test" % "4.0.0", // scala-steward:off
      "org.slf4j" % "slf4j-nop" % "1.7.32" // scala-steward:off
    ).map(_ % Test),
    Test / parallelExecution := false
  )

lazy val scalikejdbc35 = project
  .in(file("scalikejdbc35"))
  .settings(
    name := "enumeratum-scalikejdbc35",
    scalaVersion := "2.13.14",
    crossScalaVersions := Seq("2.13.14", "2.12.19"),
    scalacOptions ++= Seq("-deprecation"),
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % "1.7.4",
      "org.scalikejdbc" %% "scalikejdbc" % "3.5.0" % Provided // scala-steward:off
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.2.224",
      "org.scalatest" %% "scalatest" % "3.2.15", // scala-steward:off
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.5.0", // scala-steward:off
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

lazy val root = project
  .in(file("."))
  .withId("enumeratum-scalikejdbc")
  .settings(publish / skip := true)
  .aggregate(scalikejdbc43, scalikejdbc4, scalikejdbc35)
