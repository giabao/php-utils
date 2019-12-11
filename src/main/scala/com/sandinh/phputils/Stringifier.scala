package com.sandinh.phputils

import scala.collection.mutable.ArrayBuffer
import java.lang.reflect.Modifier
import java.nio.charset.Charset

private class SerializeException(s: String) extends Exception

private class Stringifier {
  private val charset = Charset.forName("UTF-8")
  private val refs = ArrayBuffer.empty[Any]
  private var php64: Boolean = true
  def write(o: Object, _php64bit: Boolean = true): String = {
    val buffer = new StringBuffer()
    php64 = _php64bit
    stringifyObject(o, buffer)
    buffer.toString
  }

  private def stringifyObject(o: Any, buffer: StringBuffer, allowReference: Boolean = true) = {
    if (o == null) stringifyNull(buffer)
    else if (allowReference && stringifyReference(o, buffer)) true
    else o match {
      case s: String       => stringifyString(s, buffer)
      case s: Char         => stringifyCharacter(s, buffer)
      case s: Int          => stringifyInteger(s, buffer)
      case s: Short        => stringifyInteger(s.toInt, buffer)
      case s: Byte         => stringifyInteger(s.toInt, buffer)
      case s: Long         => stringifyLong(s, buffer)
      case s: Double       => stringifyDouble(s, buffer)
      case s: Float        => stringifyFloat(s, buffer)
      case s: Boolean      => stringifyBoolean(s, buffer)
      case s: Array[_]     => stringifyArray(s, buffer)
      case s: Map[_, _]    => stringifyMap(s, buffer)
      case s: Iterable[_]  => stringifyCollection(s, buffer)
      case s: Serializable => stringifySerializable(s, buffer)
      case _               => throw new SerializeException("Unable to stringify " + o.getClass.getName)
    }
    refs += o
  }

  private def stringifyReference(o: Any, buffer: StringBuffer): Boolean = {
    // Don't allow references for simple types
    if (o.isInstanceOf[Number] || o.isInstanceOf[Boolean] || o.isInstanceOf[String]) return false
    val index = refs.indexOf(o)
    if (index != -1) {
      buffer.append("R:").append(index + 1).append(";")
      true
    } else false
  }

  private def stringifyString(string: String, buffer: StringBuffer): Unit = {
    val decoded: String = decode(string, charset)
    buffer.append("s:").append(decoded.length).append(":\"").append(string).append("\";")
  }

  private def stringifyCharacter(value: Char, buffer: StringBuffer): Unit = {
    buffer.append("s:1:\"").append(value).append("\";")
  }

  private def stringifyNull(buffer: StringBuffer): Unit = {
    buffer.append("N;")
  }

  private def stringifyInteger(number: Int, buffer: StringBuffer): Unit = {
    buffer.append("i:").append(number).append(";")
  }

  private def stringifyLong(number: Long, buffer: StringBuffer): Unit = {
    if ((number >= Integer.MIN_VALUE) && (number <= Integer.MAX_VALUE)) buffer.append("i:")
    else if (php64) buffer.append("i:")
    else buffer.append("d:")
    buffer.append(number).append(";")
  }

  private def stringifyDouble(number: Double, buffer: StringBuffer): Unit = {
    buffer.append("d:").append(number).append(";")
  }

  private def stringifyFloat(number: Float, buffer: StringBuffer): Unit = {
    buffer.append("d:").append(number).append(";")
  }

  private def stringifyBoolean(value: Boolean, buffer: StringBuffer): Unit = {
    buffer.append("b:").append(if (value) 1 else 0).append(";")
  }

  private def stringifyCollection(collection: Iterable[_], buffer: StringBuffer): Unit = {
    refs += collection
    buffer.append("a:").append(collection.size).append(":{")
    val iterator = collection.iterator
    var index = 0
    while (iterator.hasNext) {
      stringifyObject(index, buffer, allowReference = false)
      refs.trimEnd(1)
      stringifyObject(iterator.next(), buffer)
      index += 1
    }
    buffer.append('}')
  }

  private def stringifyArray(array: Array[_], buffer: StringBuffer): Unit = {
    refs += array
    val size = array.length
    buffer.append("a:").append(size).append(":{")
    for (i <- 0 until size) {
      stringifyObject(i, buffer, allowReference = false)
      refs.trimEnd(1)
      stringifyObject(array(i), buffer)
    }
    buffer.append('}')
  }

  private def stringifyMap(map: Map[_, _], buffer: StringBuffer): Unit = {
    refs += map
    buffer.append("a:").append(map.size).append(":{")
    map.foreach(e => {
      stringifyObject(e._1, buffer, allowReference = false)
      refs.trimEnd(1)
      stringifyObject(e._2, buffer)
    })
    buffer.append('}')
  }

  private def stringifySerializable(o: Serializable, buffer: StringBuffer): Unit = {

    refs += o
    var c: Class[_] = o.getClass
    val className = c.getName.replace('.', '\\')
    buffer.append("O:").append(className.length()).append(":\"").append(className).append("\":")

    val fieldBuffer = new StringBuffer()
    var fieldCount = 0
    while (c != null) {
      val fields = c.getDeclaredFields
      for (i <- 0 until fields.length) {
        val field = fields(i)
        if (!Modifier.isStatic(field.getModifiers) || !Modifier.isVolatile(field.getModifiers))
          try {
            field.setAccessible(true)
            stringifyObject(field.getName, fieldBuffer)
            refs.trimEnd(1)
            stringifyObject(field.get(o), fieldBuffer)
            fieldCount += 1
          } catch {
            case e: Exception => println("unable stringify " + className)
          }
      }
      c = c.getSuperclass
    }
    buffer.append(fieldCount).append(":{").append(fieldBuffer).append("}")
  }
}

