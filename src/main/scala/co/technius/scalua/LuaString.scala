package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaString => LuajString}
import scala.language.implicitConversions

class LuaString(override val wrapped: LuajString) extends LuaValue(wrapped) {
  def this(value: String) = this(LuajValue.valueOf(value))
}

object LuaString {
  def apply(value: String) = new LuaString(value)

  def unapply(value: LuaValue): Option[String] = value match {
    case _: LuaString => Some(value.toString)
    case _ => None
  }

  implicit def fromLuaj(value: LuajString): LuaString = new LuaString(value)
}
