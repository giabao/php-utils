import com.sandinh.phputils.PhpObject
import PhpObject.stringify
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StringifySuite extends AnyFlatSpec with Matchers {
  it should "from long, int, short, byte" in {
    //PHP 64bit will serialize long to `i`
    stringify(123456789012345L, php64bit = true) shouldBe "i:123456789012345;"
    //PHP 32bit will serialize long to `d`
    stringify(123456789012345L, php64bit = false) shouldBe "d:123456789012345;"
    stringify(123) shouldBe "i:123;"
    stringify(123.toShort) shouldBe "i:123;"
    stringify(123.toByte) shouldBe "i:123;"
  }

  it should "from double, float" in {
    stringify(123.4567) shouldBe "d:123.4567;"
    stringify(123.4567F) shouldBe "d:123.4567;"
  }

  it should "from string, char" in {
    stringify("This is a string") shouldBe """s:16:"This is a string";"""

    stringify("Tiếng Việt") shouldBe """s:14:"Tiếng Việt";"""

    stringify('a') shouldBe """s:1:"a";"""
  }

  it should "from map" in {
    stringify(Map("a" -> "hello", "b" -> "boy")) shouldBe """a:2:{s:1:"a";s:5:"hello";s:1:"b";s:3:"boy";}"""
    stringify(Map("k1" -> "value1", "k2" -> "Tiếng Việt")) shouldBe """a:2:{s:2:"k1";s:6:"value1";s:2:"k2";s:14:"Tiếng Việt";}"""
  }

  it should "from array" in {
    stringify(Array("viet", "nam")) shouldBe """a:2:{i:0;s:4:"viet";i:1;s:3:"nam";}"""
  }

  it should "from array 2" in {
    stringify(Array(123, "abc")) shouldBe """a:2:{i:0;i:123;i:1;s:3:"abc";}"""
  }

  it should "from object" in {
    stringify(new DummyClass("abc", 123, 0.999)) shouldBe
      """O:10:"DummyClass":3:{s:4:"var1";s:3:"abc";s:4:"var2";i:123;s:4:"var3";d:0.999;}"""
  }
}

private class DummyClass(val var1: String, val var2: Int, val var3: Double) extends Serializable
