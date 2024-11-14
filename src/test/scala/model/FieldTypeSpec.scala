import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.FieldType

class FieldTypeSpec extends AnyWordSpec with Matchers {

  "FieldType Enumeration" should {

    "contain four defined values: START, GAME, HOME, and STARTHOUSE" in {
      FieldType.values should contain allOf (FieldType.START, FieldType.GAME, FieldType.HOME, FieldType.STARTHOUSE)
    }

    "have START as a valid enumeration value" in {
      FieldType.START.toString shouldBe "START"
    }

    "have GAME as a valid enumeration value" in {
      FieldType.GAME.toString shouldBe "GAME"
    }

    "have HOME as a valid enumeration value" in {
      FieldType.HOME.toString shouldBe "HOME"
    }

    "have STARTHOUSE as a valid enumeration value" in {
      FieldType.STARTHOUSE.toString shouldBe "STARTHOUSE"
    }
  }
}
