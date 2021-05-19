ThisBuild / organization := props.Organization
ThisBuild / version := props.ProjectVersion
ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / developers := List(
  Developer("Kevin-Lee", "Kevin Lee", "kevin.code@kevinlee.io", url("https://github.com/Kevin-Lee"))
)
ThisBuild / scmInfo :=
  Some(
    ScmInfo(
      url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}"),
      s"https://github.com/${props.GitHubUsername}/${props.RepoName}.git",
    )
  )

lazy val canEqualsRoot = (project in file("."))
  .settings(
    name := s"${props.RepoName}-root",
  )
  .settings(
    noPublish
  )
  .aggregate(
    canEquals
  )

lazy val canEquals = Project(props.RepoName, file(props.RepoName))
  .settings(
    name := props.RepoName,
    libraryDependencies ++= libs.hedgehog,
    testFrameworks ~= (testFws => TestFramework("hedgehog.sbt.Framework") +: testFws)
  )

lazy val props =
  new {
    final val ProjectVersion = "0.1.0"

    final val ProjectScalaVersion = "3.0.0"

    final val Organization   = "io.kevinlee"
    final val GitHubUsername = "Kevin-Lee"
    final val RepoName       = "can-equals"

    final val HedgehogVersion = "0.7.0"

  }

lazy val libs =
  new {
    lazy val hedgehog = Seq(
      "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion,
      "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion,
      "qa.hedgehog" %% "hedgehog-sbt"    % props.HedgehogVersion
    )
  }
