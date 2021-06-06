package example

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import example.Fs2Example.eff
import fs2.{INothing, Pure, Stream}

import scala.collection.immutable

object Fs2Example {
  val streamEmpty: Stream[Pure, INothing] = Stream.empty
  val streamOne: Stream[Pure, Int] = Stream.emit(1)
  val streamThree: Stream[Pure, Int] = Stream(1, 2, 3)
  val streamList: Stream[Pure, List[Int]] = Stream(List(1, 2, 3))
  val listFromStreamThree: immutable.Seq[Int] = streamThree.toList

  val filteredStream: Stream[Pure, Int] = Stream(1, 2, 3, 4, 5, 6).filter(_ > 0)
  val foldSum: List[Int] = Stream(1, 2, 3).fold(0)(_ + _).toList
  val repeat: Stream[Pure, Int] = Stream(1, 2, 3).repeat.take(9)
  val collect: Stream[Pure, Int] = Stream(None, Some(0), Some(1)).collect { case Some(i) => 1 }

  val eff: Stream[IO, Int] = Stream.eval(IO {
    println("Hello! FS2");
    1 + 1
  })


}

object Main extends App {

  val vector: IO[Vector[Int]] = eff.compile.toVector
  val foldResult: IO[Int] = eff.compile.fold(0)(_ + _)
  val drain: IO[Unit] = eff.compile.drain

  vector.unsafeRunSync
}

object MainOperator extends App {

  val result = (Stream(Some(1), Some(2)) ++ Stream(Some(3), Some(4), None))
    .flatMap(i => Stream(i, i))
    .map {
      case Some(i) => i
      case _ => -1
    }.map(_ + 1)

  result.toList.foreach(println)
}

object MainError extends App {
  val err = Stream.raiseError[IO](new Exception("Oops! 1"))
  val err2 = Stream(1, 2, 3) ++ (throw new Exception("Oops! 2"))
  val err3 = Stream.eval(IO(throw new Exception("Oops! 3")))


  try err.compile.toList.unsafeRunSync() catch {
    case e: Exception => println(e)
  }

  try err2.toList catch {
    case e: Exception => println(e)
  }

  try err3.compile.drain.unsafeRunSync() catch {
    case e: Exception => println(e)
  }
}