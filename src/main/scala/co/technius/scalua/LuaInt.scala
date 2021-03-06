package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaInteger => LuajInt}
import scala.language.implicitConversions

class LuaInt(override val wrapped: LuajInt) extends LuaNumber(wrapped) {
  type LuaInteger = LuaInt

  def this(value: Int) = this(LuajValue.valueOf(value))
}

object LuaInt {
  def apply(value: Int) = new LuaInt(value)

  def unapply(value: LuaValue): Option[Int] = value match {
    case _: LuaInt => Some(value.wrapped.toint)
    case _ => None
  }

  implicit def fromLuaj(value: LuajInt) = new LuaInt(value)
}
