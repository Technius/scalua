package co.technius.scalua

import scala.language.experimental.macros

trait LuaConverter[T] {
  def toJava(value: LuaValue): Option[T] = None
  def toLua(value: T): LuaValue = ???
}

object LuaConverter {
  def generate[T] = macro MacroImpl.generateConverterImpl[T]
}
