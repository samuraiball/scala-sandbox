package example

import cats.effect.{IO, IOApp}
import fs2.io.file.Files
import fs2.{Stream, text}

import java.nio.file.Paths

object Hello extends IOApp.Simple {

  override def run: IO[Unit] =
    converter.compile.drain

  val converter: Stream[IO, Unit] = {
    def fahrenheitToCelsius(f: Double): Double =
      (f - 32.0) * (5.0 / 9.0)

    Files[IO].readAll(Paths.get("testdata/src.txt"), 4096)
      .through(text.utf8Decode)
      .through(text.lines)
      .filter(s => !s.trim.isEmpty && !s.startsWith("//"))
      .map(line => fahrenheitToCelsius(line.toDouble).toString)
      .intersperse("\n")
      .through(text.utf8Encode)
      .through(Files[IO].writeAll(Paths.get("testdata/dest.txt")))
  }
}

