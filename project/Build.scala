import sbt._

import Keys._
import AndroidKeys._
import AndroidNdkKeys._

object General {
  // Some basic configuration
  val settings = Defaults.defaultSettings ++ Seq (
    name := "sensor-proxy-client",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.10.1",
    platformName in Android := "android-16"
  )

  // Default Proguard settings
  lazy val proguardSettings = inConfig(Android) (Seq (
    useProguard := true,
    proguardOptimizations += "-keep class cs.luc.edu.client.** { *; }"
  ))

  // Example NDK settings
  lazy val ndkSettings = AndroidNdk.settings ++ inConfig(Android) (Seq(
    jniClasses := Seq(),
    javahOutputFile := Some(new File("native.h"))
  ))

  // Full Android settings
  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",


		resolvers += "spray repo" at "http://repo.spray.io",

      libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "1.9.1" % "test",
"io.spray" %%  "spray-json" % "1.2.3"))
}
	  
   

object AndroidBuild extends Build {
  lazy val main = Project (
    "main",
    file("."),
    settings = General.fullAndroidSettings ++ AndroidEclipseDefaults.settings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidEclipseDefaults.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "sensor-proxy-clientTests"
    )
  ) dependsOn main
}
