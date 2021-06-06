package example

import cats.effect.{IO, IOApp}
import io.circe.generic.auto._
import org.http4s.circe.jsonOf
import org.http4s.client.blaze._
import org.http4s.client.dsl.io._
import org.http4s.dsl.io.GET
import org.http4s.headers.Accept
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{EntityDecoder, MediaType}

import scala.concurrent.ExecutionContext.Implicits.global

object ClientSample extends IOApp.Simple {

  case class ToDoResponse(userId: Int, id: Int, title: String, completed: Boolean)

  implicit val toDoResponseDecoder: EntityDecoder[IO, ToDoResponse] = jsonOf[IO, ToDoResponse]

  override def run: IO[Unit] = BlazeClientBuilder[IO](global).resource.use { client =>
    val request = GET(
      uri"https://jsonplaceholder.typicode.com/todos/1",
      Accept(MediaType.application.json)
    )
    val value = client.expect[ToDoResponse](request)

    val str: ToDoResponse = value.unsafeRunSync()
    println(str)
    IO.unit
  }
}
//
