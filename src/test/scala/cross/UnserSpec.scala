package cross

import com.sandinh.phputils.PhpObject.parse
import java.io.{BufferedInputStream, FileInputStream}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import sys.process._

class UnserSpec extends AnyFlatSpec with Matchers {
  def unser(): Any = {
    val bis = new BufferedInputStream(new FileInputStream(Data.dataFile))
    val bytes = Stream.continually(bis.read).takeWhile(_ != -1).map(_.toByte).toArray
    val ret = parse(bytes)
    bis.close()
    ret
  }

  "php src/test/php/ser.php".!!

  "Parser" should "ser from PHP should be validated in PHP unser" in {
    //if php test success => exit code == 0
    //else => exit code == 1
    // => "RuntimeException: Nonzero exit value: 1"
    // => test fail
    "php src/test/php/unser.php".!!
  }

  "Parser" should "ser from PHP should be validated in SCALA unser" in {
    val actual = unser()
    assert(actual.isInstanceOf[Map[_, _]], "parsed value must be Map")
    val r = actual.asInstanceOf[Map[_, _]]
    r should contain theSameElementsAs Data.expectedData
  }
}
