abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Int) extends Expr 
case class UnOp(op: String, arg: Expr) extends Expr
case class BinOp(op: String, left: Expr,right: Expr) extends Expr


def simplifyTop(expr: Expr): Expr = expr match {
  case UnOp("-", UnOp("-", e))  => e
  case BinOp("+", e, Number(0)) => e
  case BinOp("*", e, Number(1)) => e
  case _ => expr
}

def wildCard(expr: Expr) = expr match {
  case BinOp(op, left, right) => println(s"$op,  $left,  $right")
  case _ => println("default is called")
}

def constant(x: Any) = x match {
  case 5 => "five"
  case true => "true"
  case "hello"  => "hi!"
  case Nil => "Nil"
  case _ => "somethig else"
}

def variable(x: Int) = x match {
  case 0 => "zero"
  case somethingElse => "something else"
}

def constractor(expr: Expr) = expr match {
  case BinOp("+", e, Number(0)) => println("a deep match")
  case _ => println("aaaa")
}


def sequence(list : List[Int]) = list match {
  case List(0, _, _) => println("3 list")
  case List(0, 0, _*) => println("more than 2")
  case _ =>  println("else")
}

def tuple(expr: (Expr, Expr, Expr)) = expr match {
  case (a, b, c) => println(s"matched $a, $b, $c")
  case null => println("default")
}

def withType(x: Any) = x match {
  case s: String => println(s)
 case s: Map[_, _] => println(s)
  // the type test for Map[Int, Int] cannot be checked at runtime
  // case s: Map[Int, Int] => println(s)
  case x => println(s"$x")
}

def guard(expr: Expr) = expr match {
  // error
  //case BinOp("+", x, x) => println("error")
  case BinOp("+", x, y) if x == y => println(s"$x, $y")
  case _ => println("default")
}

def withDefault: Option[Int] => Int = {
  case Some(x) => x
  case None => 0
}

@main def main() = {
  val x = simplifyTop(UnOp("-", UnOp("-", Var("x"))))
  println(x)

  wildCard(BinOp("-", Var("x"), Var("y")))

  sequence(List(0,0,0,0,0))
  sequence(List(0,0,0))
  sequence(List(0,0))
  sequence(List(0))

  tuple(BinOp("-", Var("x"), Var("y")), Var("z"), Number(0))

  withType(1)
  withType(Map("key1" -> 111, "key2" -> 222))
  withType(List(1, 2))

  guard(BinOp("+", Var("x"), Var("x")))
  
  println(withDefault(Some(10)))

}

