package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaString => LuajString}

class LuaString(_wrapped: LuajString) extends LuaValue(_wrapped) {
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
