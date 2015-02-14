name := "dropunit"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.badlogicgames.gdx" % "gdx" % "1.5.3",
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.5.3",
  "com.badlogicgames.gdx" % "gdx-platform" % "1.5.3" classifier "natives-desktop"
)

scalacOptions ++= Seq(
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-value-discard",
  "-Ywarn-numeric-widen",
  "-unchecked",
  "-deprecation",
  "-feature",
  "-encoding", "UTF-8"
)

fork in run := true