package example.server

import cats.effect._
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router


object SampleRouters {

  def helloHandler() = IO("hello")

  val helloRouter = HttpRoutes.of[IO] {
    case GET -> Root / "hello" => helloHandler().flatMap(Ok(_))
  }

  def greetingSomeone(name: String) = IO(s"Hello, $name")


  case class Name(name: String)

  case class Greeting(hello: String)

  val greetingRouter = HttpRoutes.of[IO] {
    case req@POST -> Root / "greeting" => for {
      n <- req.as[Name]
      resp <- Created(Greeting(n.name).asJson)
    } yield resp
    case GET -> Root / "greeting" / name => greetingSomeone(name).flatMap(Ok(_))
  }

  def createApp = Router("/" -> helloRouter, "/v1" -> greetingRouter).orNotFound

}


