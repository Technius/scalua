package co.technius.scalua

import scala.util.control.Exception._
import scala.reflect.macros.Context

object MacroImpl {
  def luaValueConverterImpl[T <: LuaValue: c.WeakTypeTag](
    c: Context
  ): c.Expr[LuaConverter[T]] = {
    import c.universe._
    val typ = weakTypeOf[T]
    c.Expr[LuaConverter[T]](q"""new LuaConverter[$typ] {
      override def toJava(value: LuaValue): Option[$typ] = value match {
        case x: $typ => Some(x)
        case _ => None
      }

      override def toLua(value: $typ): LuaValue = value
    }
    """)
  }
}
