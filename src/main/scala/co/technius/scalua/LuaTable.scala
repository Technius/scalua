package co.technius.scalua

import org.luaj.vm2.{LuaValue => LuajValue, LuaTable => LuajTable}
import scala.collection.mutable.ListBuffer
import scala.language.implicitConversions

class LuaTable(override val wrapped: LuajTable) extends LuaValue(wrapped) {
  def next(key: LuaValue): (LuaValue, LuaValue) = {
    val tmp = wrapped.next(key.wrapped)
    (LuaValue(tmp.arg1), LuaValue(tmp.arg(2)))
  }

  def toMap: Map[LuaValue, LuaValue] = {
    val b = new ListBuffer[(LuaValue, LuaValue)]()
    def mapImpl(current: LuaValue): Unit = next(current) match {
      case (LuaNil, _) => Unit
      case keypair => {
        b += keypair
        mapImpl(keypair._1)
      }
    }
    mapImpl(LuaNil)
    b.toMap
  }
}

object LuaTable {
  def apply(): LuaTable = new LuaTable(LuajValue.tableOf())
  implicit def fromLuaj(value: LuajTable): LuaTable = new LuaTable(value)
}
