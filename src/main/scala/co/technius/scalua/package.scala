package co.technius

import scala.util.control.Exception._
import scala.language.experimental.macros

package object scalua {
  implicit object IntConverter extends LuaConverter[Int] {
    def toJava(value: LuaValue): Option[Int] = value match {
      case LuaInt(i) => Some(i)
      case _ => None
    }
  }

  implicit object FloatConverter extends LuaConverter[Float] {
    def toJava(value: LuaValue): Option[Float] = value match {
      case LuaInt(i) => Some(i.toFloat)
      case LuaDouble(d) => Some(d.toFloat)
      case _ => None
    }
  }

  implicit object DoubleConverter extends LuaConverter[Double] {
    def toJava(value: LuaValue): Option[Double] = value match {
      case LuaInt(i) => Some(i.toDouble)
      case LuaDouble(d) => Some(d)
      case _ => None
    }
  }

  implicit object StringConverter extends LuaConverter[String] {
    def toJava(value: LuaValue): Option[String] = value match {
      case LuaString(s) => Some(s)
      case _ => None
    }
  }

  implicit object BooleanConverter extends LuaConverter[Boolean] {
    def toJava(value: LuaValue): Option[Boolean] = value match {
      case LuaBoolean(b) => Some(b)
      case _ => None
    }
  }

  implicit def luaValueConverter[T <: LuaValue]: LuaConverter[T] = macro lvConverterImpl[T]

  import scala.reflect.macros.Context
  def lvConverterImpl[T <: LuaValue: c.WeakTypeTag](
    c: Context
  ): c.Expr[LuaConverter[T]] = {
    import c.universe._
    val typ = weakTypeOf[T]
    c.Expr[LuaConverter[T]](q"""new LuaConverter[$typ] {
      def toJava(value: LuaValue): Option[$typ] = value match {
        case x: $typ => Some(x)
        case _ => None
      }
    }
    """)
  }
}
