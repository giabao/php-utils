package cross

import com.sandinh.phputils.PhpObject
import PhpObject.stringify
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import org.scalatest.funsuite.AnyFunSuite
import sys.process._

class SerSpec extends AnyFunSuite {
  def ser(): Unit = {
    val out = new FileOutputStream(Data.dataFile)
    out.write(stringify(Data.data).getBytes(UTF_8))
    out.close()
  }

  test("ser from SCALA should be validated in PHP unser") {
    ser()
    "php src/test/php/unser.php".!!
  }
}
