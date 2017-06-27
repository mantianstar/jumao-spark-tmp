name := "ExportGA"

version := "1.0"

scalaVersion := "2.11.11"

lazy val sparkVer = "2.1.0"
lazy val scalaVer = "2.11"
lazy val hbaseVer = "1.2.5"
lazy val slf4jVer = "1.7.21"


//resolvers += "Maven Central" at "http://repo1.maven.org/maven2"

libraryDependencies ++= Seq(
    "org.apache.spark" % s"spark-core_$scalaVer" % sparkVer,
    "org.apache.spark" % s"spark-sql_$scalaVer" % sparkVer,
    "org.apache.spark" % s"spark-yarn_$scalaVer" % sparkVer,
    "org.apache.hbase" % "hbase-client" % hbaseVer,
    "org.apache.hbase" % "hbase-common" % hbaseVer,
    "org.apache.hbase" % "hbase-server" % hbaseVer,

    "com.google.apis" % "google-api-services-analyticsreporting" % "v4-rev114-1.22.0",
    "com.google.api-client" % "google-api-client-gson" % "1.22.0"
).map(_.exclude("org.slf4j", "*")).map(_.exclude("com.google.guava", "*"))


libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-log4j12" % slf4jVer,
    "org.slf4j" % "slf4j-api" % slf4jVer,
    "org.slf4j" % "slf4j-simple" % slf4jVer,
    "uk.org.lidalia" % "jul-to-slf4j-config" % "1.0.0",
    "org.apache.logging.log4j" % "log4j-core" % "2.8.1",

    "commons-codec" % "commons-codec" % "1.10",
    "commons-io" % "commons-io" % "2.5",
    "org.codehaus.jettison" % "jettison" % "1.3.8",
    "com.google.guava" % "guava" % "16.0.1",
    "javax.servlet" % "servlet-api" % "2.5" % "provided"

)