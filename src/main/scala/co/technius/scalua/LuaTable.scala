package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaTable => LuajTable}

class LuaTable(_wrapped: LuajTable) extends LuaValue(_wrapped) {
  def next(key: LuaValue): (LuaValue, LuaValue) = {
    val tmp = _wrapped.next(key.wrapped)
    (LuaValue(tmp.arg1), LuaValue(tmp.arg(2)))
  }
}

object LuaTable {
  def apply(): LuaTable = new LuaTable(LuajValue.tableOf())
  implicit def fromLuaj(value: LuajTable): LuaTable = new LuaTable(value)
}
