ThisBuild / organization := props.Organization
ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / developers   := List(
  Developer(
    props.GitHubUsername,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUsername}")
  )
)
ThisBuild / homepage     := url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}").some
ThisBuild / scmInfo      :=
  Some(
    ScmInfo(
      url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}"),
      s"https://github.com/${props.GitHubUsername}/${props.RepoName}.git",
    )
  )
ThisBuild / licenses     := props.licenses

lazy val canEqualRoot = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    name     := s"${props.RepoName}-root",
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
    name     := props.RepoName,
    libraryDependencies ++= libs.hedgehog,
    testFrameworks ~= (testFws => TestFramework("hedgehog.sbt.Framework") +: testFws),
    licenses := props.licenses,
  )

lazy val props =
  new {

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
      "qa.hedgehog" %% "hedgehog-core"   % props.HedgehogVersion % Test,
      "qa.hedgehog" %% "hedgehog-runner" % props.HedgehogVersion % Test,
      "qa.hedgehog" %% "hedgehog-sbt"    % props.HedgehogVersion % Test,
    )
  }
