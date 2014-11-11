package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaNumber => LuajNumber}
import scala.language.implicitConversions

abstract class LuaNumber(override val wrapped: LuajNumber) extends LuaValue(wrapped) {
  def toInt: Int = wrapped.toint

  def toDouble: Double = wrapped.todouble
}

object LuaNumber {
  def apply(value: Int) = new LuaInt(value)

  def apply(value: Double) = new LuaDouble(value)

  def apply(value: Float) = new LuaDouble(value.toDouble)
}
