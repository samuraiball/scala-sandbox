import org.scalatest.funsuite.AnyFunSuite

class CalcFunSuiteTest extends AnyFunSuite {
  test("足し算の結果を返す") {
    val c = new Calc
    assert(c.plus(1, 2) == 3)
  }
}
