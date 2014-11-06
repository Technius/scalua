package co.technius.scalua

import org.luaj.vm2.{LuaDouble => LuajDouble}

class LuaDouble(_wrapped: LuajDouble) extends LuaValue(_wrapped) {
  def this(value: Double) = this(LuajDouble.valueOf(value).asInstanceOf[LuajDouble])
}

object LuaDouble {
  def apply(value: Double) = new LuaDouble(value)

  def unapply(value: LuaValue): Option[Double] = value match {
    case _: LuaDouble => Some(value.wrapped.todouble)
    case _ => None
  }

  implicit def fromLuaj(value: LuajDouble) = new LuaDouble(value)
}
