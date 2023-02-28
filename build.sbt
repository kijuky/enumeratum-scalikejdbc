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
          Seq("-Xlint")
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
      "org.scalatest" %% "scalatest" % "3.2.15",
      "org.scalikejdbc" %% "scalikejdbc-test" % "4.0.0"
    ).map(_ % Test),
    Test / parallelExecution := false
  )

lazy val scalikejdbc35 = (project in file("scalikejdbc35"))
  .settings(
    name := "enumeratum-scalikejdbc35",
    scalaVersion := "2.13.10",
    crossScalaVersions := Seq("2.13.10", "2.12.17"),
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 13)) =>
          Seq("-Xlint")
        case _ =>
          Seq("-Xlint")
      }
    },
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % "1.7.2",
      "org.scalikejdbc" %% "scalikejdbc" % "3.5.0" % Provided
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.1.214",
      "org.scalatest" %% "scalatest" % "3.2.15",
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.5.0"
    ).map(_ % Test),
    Test / parallelExecution := false
  )

lazy val scalikejdbc34 = (project in file("scalikejdbc34"))
  .settings(
    name := "enumeratum-scalikejdbc35",
    scalaVersion := "2.13.10",
    crossScalaVersions := Seq("2.13.10", "2.12.17", "2.11.12"),
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 13)) =>
          Seq("-Xlint")
        case _ =>
          Seq("-Xlint")
      }
    },
    libraryDependencies ++= Seq(
      "com.beachape" %% "enumeratum" % "1.7.2",
      "org.scalikejdbc" %% "scalikejdbc" % "3.4.2" % Provided
    ),
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % "2.1.214",
      "org.scalatest" %% "scalatest" % "3.0.9",
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.4.2"
    ).map(_ % Test),
    Test / parallelExecution := false
  )

lazy val root = (project in file(".") withId "enumeratum-scalikejdbc")
  .aggregate(scalikejdbc4, scalikejdbc35, scalikejdbc34)
