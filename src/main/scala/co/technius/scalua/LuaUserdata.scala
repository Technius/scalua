package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaUserdata => LuajUserdata}
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import scala.language.implicitConversions

class LuaUserdata(override val wrapped: LuajUserdata) extends LuaValue(wrapped) {
  def data: Any = wrapped.touserdata

  def dataAs[T]: T = wrapped.touserdata.asInstanceOf[T]
}

object LuaUserdata {
  def apply(obj: Any): LuaUserdata = LuajValue.userdataOf(obj)

  def apply(obj: Any, metatable: LuaValue): LuaUserdata = LuajValue.userdataOf(obj, metatable.wrapped)

  def coerce(obj: Any): LuaValue = LuaValue(CoerceJavaToLua.coerce(obj))

  def unapply(value: LuaValue): Option[Any] = value match {
    case _: LuaUserdata => Some(value.wrapped.touserdata)
    case _ => None
  }

  implicit def fromLuaj(value: LuajUserdata): LuaUserdata = new LuaUserdata(value)
}
