import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class CalcSpec extends AnyFlatSpec with should.Matchers {

  it should "足し算の結果を返す" in {
    val c = new Calc
    c.plus(1, 2) should be(3)
  }

  it should "負の数が引数に渡された場合にIllegalArgumentException" in {
    val c = new Calc
    a[IllegalArgumentException] should be thrownBy {
      c.plus(1, -2)
    }
  }
}
