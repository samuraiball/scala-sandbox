import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should

class PersonSpec extends AsyncFlatSpec with should.Matchers with AsyncMockFactory {

  it should "Languageをモックする1" in {
    val mockLang = mock[Language]
    (mockLang.greeting _).expects().returning("Hello, ScalaMock!").once()

    val target = new Person(mockLang)
    target.saySomeThing() should be("Hello, ScalaMock!")
  }


  it should "Languageをモックする2" in {
    val mockLang = mock[Language]
    (mockLang.greeting _).expects().returning("Hello, ScalaMock!!").once()

    val target = new Person(mockLang)
    target.saySomeThing() should be("Hello, ScalaMock!!")
  }
}
