package example.server

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.Implicits.global



object AppStarter extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8081, "localhost")
      .withHttpApp(SampleRouters.createApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

}
