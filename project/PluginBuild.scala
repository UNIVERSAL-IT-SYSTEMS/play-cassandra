/*
 * Licensed to Tuplejump Software Pvt. Ltd. under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Tuplejump Software Pvt. Ltd. licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import sbt._
import sbt.Keys._

object PluginBuild extends Build {


  lazy val pom = {
    <scm>
      <url>git@github.com:tuplejump/play-cassandra.git</url>
      <connection>scm:git:git@github.com:tuplejump/play-cassandra.git</connection>
    </scm>
      <developers>
        <developer>
          <id>Shiti</id>
          <name>Shiti Saxena</name>
          <url>https://twitter.com/eraoferrors</url>
        </developer>
	<developer>
          <id>milliondreams</id>
          <name>Rohit Rai</name>
          <url>https://twitter.com/milliondreams</url>
        </developer>
      </developers>
  }

  val playVersion = "2.3.0"

  val casVersion = "2.0.4"

  lazy val cassandraPlugin = Project(
    id = "play-cassandra",
    base = file("."),
    settings = Seq(
      name := "play-cassandra",
      organization := "com.tuplejump",
      version := "0.0.2",
      scalaVersion := "2.10.4",
      crossScalaVersions := Seq("2.10.4", "2.11.2"),
      crossVersion := CrossVersion.binary,
      resolvers := Seq(
        "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
        "Sonatype" at "http://oss.sonatype.org/content/groups/public/",
        "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
        "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      ),
      libraryDependencies ++= Seq(
        "com.datastax.cassandra" % "cassandra-driver-core" % casVersion,
        "com.typesafe.play" %% "play" % playVersion % "provided" cross CrossVersion.binary
      ),
    pomExtra := pom,
    publishArtifact in Test := false,
    pomIncludeRepository := {
      _ => false
    },
    publishMavenStyle := true,
    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.io/tuplejump/play-cassandra")),
    organizationName := "Tuplejump, Inc.",
    organizationHomepage := Some(url("http://www.tuplejump.com"))
    )
  )
}
