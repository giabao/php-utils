import com.sandinh.phputils.PhpObject
import PhpObject.parse
import org.scalatest.funsuite.AnyFunSuite

class ParserSuite extends AnyFunSuite {
  test("to string") {
    val data = "s:3:\"abc\";"
    parse(data) match {
      case "abc" => //ok
      case _     => fail()
    }
  }
  test("to int") {
    parse("i:123456;") match {
      case d: Int if d == 123456 => //ok
      case _                     => fail()
    }
  }
  test("to long") {
    parse("i:123456789012345;") match {
      case d: Long if d == 123456789012345L => //ok
      case _                                => fail()
    }
  }
  test("to double") {
    parse("d:123.456;") match {
      case d: Double if d == 123.456 => //ok
      case _                         => fail()
    }
  }
  test("to double 2") {
    parse("d:123456789012345;") match {
      case d: Double if d == 123456789012345D => //ok
      case _                                  => fail()
    }
  }
  test("to boolean - true") {
    parse("b:0;") match {
      case false => //ok
      case _     => fail()
    }
  }
  test("to boolean - false") {
    parse("b:1;") match {
      case true => //ok
      case _    => fail()
    }
  }
  test("to array") {
    val data = """a:3:{i:0;s:2:"e1";i:1;b:1;i:2;s:2:"e2";}"""
    parse(data) match {
      case result: Map[_, _] => assert(result == Map(0 -> "e1", 1 -> true, 2 -> "e2"))
      case _                 => fail()
    }
  }
  test("to array 2") {
    val data = """a:2:{s:2:"k1";s:6:"value1";s:2:"k2";s:14:"Tiếng Việt";}"""
    parse(data) match {
      case result: Map[_, _] => assert(result == Map("k1" -> "value1", "k2" -> "Tiếng Việt"))
      case _                 => fail()
    }
  }
}
