name := "tictactoeserver"

version := "1.0"

lazy val `tictactoeserver` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs )

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"

libraryDependencies += "javax.inject" % "javax.inject" % "1"


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  