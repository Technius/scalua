package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaBoolean => LuajBoolean}

class LuaBoolean(_wrapped: LuajBoolean) extends LuaValue(_wrapped) {
  def this(value: Boolean) = this(LuajValue.valueOf(value))
}

object LuaBoolean {
  def apply(value: Boolean) = new LuaBoolean(value)

  def unapply(value: LuaValue): Option[Boolean] = value match {
    case _: LuaBoolean => Some(value.wrapped.toboolean)
    case _ => None
  }

  implicit def fromLuaj(value: LuajBoolean): LuaBoolean = new LuaBoolean(value)
}
