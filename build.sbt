name := "tictactoeserver"

version := "1.0"

lazy val `tictactoeserver` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs )

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  