

object FileMatcher {
  private def fileHere = (new java.io.File(".")).listFiles()

  def fileMatching(matcher: (String) => Boolean) = {
    for (file <- fileHere if matcher(file.getName)) yield file
  }

  def fileEnding(query: String) =
    fileMatching(_.endsWith(query))

  def fileContaining(query: String) =
    fileMatching(_.contains(query))

  def fileRegex(query: String) =
    fileMatching(_.matches(query))

}

@main def main(args: String*) = FileMatcher.fileContaining(args(0)).foreach(println)
