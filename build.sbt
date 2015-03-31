name := "gs-twitter"

version := "1.0"

resolvers += "Atilika Open Source repository" at "http://www.atilika.org/nexus/content/repositories/atilika"

libraryDependencies ++= Seq(
  "org.atilika.kuromoji" % "kuromoji" % "0.7.7"
)
