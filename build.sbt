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
ThisBuild / licenses := props.licenses

lazy val canEqualRoot = (project in file("."))
  .settings(
    name := s"${props.RepoName}-root",
    licenses := props.licenses,
  )
  .settings(
    noPublish
  )
  .aggregate(
    canEqual
  )

lazy val canEqual = Project(props.RepoName, file(props.RepoName))
  .settings(
    name := props.RepoName,
    libraryDependencies ++= libs.hedgehog,
    testFrameworks ~= (testFws => TestFramework("hedgehog.sbt.Framework") +: testFws),
    licenses := props.licenses,
  )

lazy val props =
  new {
    final val ProjectVersion = "0.1.0"

    final val ProjectScalaVersion = "3.0.0"

    final val Organization   = "io.kevinlee"
    final val GitHubUsername = "Kevin-Lee"
    final val RepoName       = "can-equal"

    final val HedgehogVersion = "0.7.0"

    final val licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

  }

lazy val libs =
  new {
    lazy val hedgehog = Seq(
      "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion,
      "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion,
      "qa.hedgehog" %% "hedgehog-sbt"    % props.HedgehogVersion
    )
  }
