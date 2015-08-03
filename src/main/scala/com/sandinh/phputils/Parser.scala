package com.sandinh.phputils

import scala.collection.mutable.ArrayBuffer
import java.nio.charset.Charset
import scala.util.Try

private class UnserializeException(s: String) extends Exception

private class Parser(str: String) {

  private var data = ""
  private var pos: Int = 0

  private val refs = ArrayBuffer.empty[Any]
  private val charset = Charset.forName("UTF-8")

  def parse(): Any = {
    data = decode(str, charset)
    val result = data.charAt(pos) match {
      case 's' => parseString()
      case 'i' =>
        val currentPos = pos
        Try(parseInteger()).getOrElse {
          pos = currentPos
          parseLong()
        }
      case 'd' => parseDouble()
      case 'b' => parseBoolean()
      case 'N' => parseNull()
      case 'a' => return parseArray()
      case 'R' => parseReference()
      case 'O' => parseObject()
      case _   => throw new UnserializeException("Unable to parse unknown type ")
    }
    refs += result
    result
  }

  private def parseString(): String = {
    val end = data.indexOf(':', pos + 2)
    val length = data.substring(pos + 2, end).toInt
    val raw = data.substring(end + 2, end + 2 + length)
    pos = end + length + 4
    encode(raw, charset)
  }

  private def parseInteger(): Int = {
    val end = data.indexOf(';', pos + 2)
    val result = data.substring(pos + 2, end).toInt
    pos = end + 1
    result
  }

  private def parseLong(): Long = {
    val end = data.indexOf(';', pos + 2)
    val result = data.substring(pos + 2, end).toLong
    pos = end + 1
    result
  }

  private def parseDouble(): Double = {
    val end = data.indexOf(';', pos + 2)
    val result = data.substring(pos + 2, end).toDouble
    pos = end + 1
    result
  }

  private def parseReference(): Any = {
    val end = data.indexOf(';', pos + 2)
    val index = data.substring(pos + 2, end).toInt
    pos = end + 1
    refs(index - 1)
  }

  private def parseBoolean(): Boolean = {
    val result = data.charAt(pos + 2) == '1'
    pos += 4
    result
  }

  private def parseNull(): Null = {
    pos += 2
    null
  }

  private def parseArray(): Map[Any, Any] = {
    val end = data.indexOf(':', pos + 2)
    val size = data.substring(pos + 2, end).toInt
    pos = end + 2
    var map = Map.empty[Any, Any]
    refs += map
    for (i <- 0 until size) {
      val key = parse()
      refs.trimEnd(1)
      val value = parse()
      map += key -> value
    }
    pos += 1
    map
  }

  def parseObject(): (String, Map[String, Any]) = {
    val startName = data.indexOf(':', pos + 2)
    val nameLength = data.substring(pos + 2, startName).toInt
    val endName = startName + 2 + nameLength
    val className = data.substring(startName + 2, endName)
    val startField = data.indexOf(":", endName + 2)
    val fieldCount = data.substring(endName + 2, startField).toInt
    pos = startField + 2
    var map = Map.empty[String, Any]
    refs += map
    for (i <- 0 until fieldCount) {
      val key = parseString()
      refs.trimEnd(1)
      val value = parse()
      map += key -> value
    }
    pos += 1
    (className, map)
  }

}
