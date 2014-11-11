package co.technius.scalua

import org.luaj.vm2.{LuaDouble => LuajDouble}
import scala.language.implicitConversions

class LuaDouble(override val wrapped: LuajDouble) extends LuaNumber(wrapped) {
  def this(value: Double) = this(LuajDouble.valueOf(value).asInstanceOf[LuajDouble])
}

object LuaDouble {
  def apply(value: Double): LuaNumber = if (value % 1 == 0) {
    new LuaInt(value.toInt)
  } else {
    new LuaDouble(value)
  }

  def unapply(value: LuaValue): Option[Double] = value match {
    case _: LuaDouble => Some(value.wrapped.todouble)
    case _ => None
  }

  implicit def fromLuaj(value: LuajDouble) = new LuaDouble(value)
}
