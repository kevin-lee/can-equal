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

lazy val root = (project in file("."))
  .settings(
    name := props.RepoName
  )

lazy val props =
  new {
    final val ProjectVersion = "0.1.0"

    final val ProjectScalaVersion = "3.0.0"

    final val Organization = "io.kevinlee"

    final val GitHubUsername = "Kevin-Lee"
    final val RepoName       = "can-equals"
  }
