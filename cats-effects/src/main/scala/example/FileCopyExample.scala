package example

import cats.effect._
import cats.syntax.all._
import example.CopyFIle.copy

import java.io._
import scala.language.higherKinds

object CopyFIle {

  def inputStream[F[_] : Sync](f: File): Resource[F, FileInputStream] =
    Resource.fromAutoCloseable(Sync[F].delay(new FileInputStream(f)))

  def outputStream[F[_] : Sync](f: File): Resource[F, FileOutputStream] =
    Resource.fromAutoCloseable(Sync[F].delay(new FileOutputStream(f)))

  def inputOutputStreams[F[_] : Sync](in: File, out: File): Resource[F, (InputStream, OutputStream)] =
    for {
      inStream <- inputStream(in)
      outStream <- outputStream(out)
    } yield (inStream, outStream)


  def transmit[F[_] : Sync](origin: InputStream, destination: OutputStream, buffer: Array[Byte], acc: Long): F[Long] =
    for {
      amount <- Sync[F].blocking(origin.read(buffer, 0, buffer.length))
      count <- if (amount > -1) Sync[F].blocking(destination.write(buffer, 0, amount)) >> transmit(origin, destination, buffer, acc + amount)
      else Sync[F].pure(acc)
    } yield count

  def transfer[F[_] : Sync](origin: InputStream, destination: OutputStream): F[Long] =
    transmit(origin, destination, new Array[Byte](1024 * 10), 0L)

  def copy[F[_] : Sync](origin: File, destination: File): F[Long] =
    inputOutputStreams(origin, destination).use {
      case (in, out) => transfer(in, out)
    }

}

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO.unit
      orig = new File("./orig.txt")
      dest = new File("./dest.txt")
      count <- copy[IO](orig, dest)
      _ <- IO.print(s"$count bytes copied from ${orig.getPath} to ${dest.getPath}")
    } yield ExitCode.Success
}