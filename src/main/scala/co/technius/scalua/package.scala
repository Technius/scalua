package co.technius

import scala.language.experimental.macros

package object scalua {
  implicit object IntConverter extends LuaConverter[Int] {
    override def toJava(value: LuaValue): Option[Int] = value match {
      case LuaInt(i) => Some(i)
      case _ => None
    }
    
    override def toLua(value: Int): LuaValue = LuaInt(value)
  }

  implicit object FloatConverter extends LuaConverter[Float] {
    override def toJava(value: LuaValue): Option[Float] = value match {
      case LuaInt(i) => Some(i.toFloat)
      case LuaDouble(d) => Some(d.toFloat)
      case _ => None
    }

    override def toLua(value: Float): LuaValue = LuaDouble(value.toFloat)
  }

  implicit object DoubleConverter extends LuaConverter[Double] {
    override def toJava(value: LuaValue): Option[Double] = value match {
      case LuaInt(i) => Some(i.toDouble)
      case LuaDouble(d) => Some(d)
      case _ => None
    }

    override def toLua(value: Double): LuaValue = LuaDouble(value)
  }

  implicit object StringConverter extends LuaConverter[String] {
    override def toJava(value: LuaValue): Option[String] = value match {
      case LuaString(s) => Some(s)
      case _ => None
    }

    override def toLua(value: String): LuaValue = LuaString(value)
  }

  implicit object BooleanConverter extends LuaConverter[Boolean] {
    override def toJava(value: LuaValue): Option[Boolean] = value match {
      case LuaBoolean(b) => Some(b)
      case _ => None
    }

    override def toLua(value: Boolean): LuaValue = LuaBoolean(value)
  }

  implicit def luaValueConverter[T <: LuaValue]: LuaConverter[T] =
    macro MacroImpl.luaValueConverterImpl[T]
}
