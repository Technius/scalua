package co.technius.scalua

import scala.util.control.Exception._
import scala.reflect.macros.blackbox.Context

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

  def generateConverterImpl[T: c.WeakTypeTag](c: Context): c.Expr[LuaConverter[T]] = {
    import c.universe._
    val typ = weakTypeOf[T]
    val dec = typ.decls
    val methods = dec filter (_.isMethod) map (_.asMethod)
    val caseValues = methods filter (_.isCaseAccessor)

    val caseValuesAst = caseValues map { cv =>
      q"""${cv.name.toString} -> LuaValue.toLua(value.${cv.name})"""
    }

    c.Expr[LuaConverter[T]](q"""new LuaConverter[$typ] {
      override def toJava(value: LuaValue): Option[$typ] = value match {
        case ud: LuaUserdata => {
          val d = ud.data
          if (d.isInstanceOf[$typ]) Some(d.asInstanceOf[$typ]) else None
        }
        case _ => None
      }

      override def toLua(value: $typ): LuaValue = {
        val t = LuaTable(..$caseValuesAst)
        t("__index") = t
        LuaUserdata(value, t)
      }
    }
    """)
  }
}
