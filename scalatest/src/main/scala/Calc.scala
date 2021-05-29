class Calc {
  def plus(a: Int, b: Int): Int = {
    if (a < 0 || b < 0) throw new IllegalArgumentException()
    a + b
  }
}

trait Language {
  def greeting(): String = "Hello!!"
}

class Person(val lang: Language) {
  def saySomeThing(): String = lang.greeting()
}

class Japanese extends Language {
  override def greeting(): String = "こんにちは"
}



