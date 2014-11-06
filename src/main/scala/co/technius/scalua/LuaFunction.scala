package co.technius.scalua

import org.luaj.{vm2 => luaj}
import scala.language.implicitConversions

class LuaFunction(override val wrapped: luaj.LuaFunction) extends LuaValue(wrapped)

object LuaFunction {
  implicit def fromLuaj(value: luaj.LuaFunction): LuaFunction = new LuaFunction(value)

  def apply(f: List[LuaValue] => List[LuaValue]): LuaFunction = new Function(f)

  def singleReturn(f: List[LuaValue] => LuaValue): LuaFunction = new Function(f andThen (List(_)))
}

sealed class Function(f: List[LuaValue] => List[LuaValue]) extends luaj.LuaFunction {
  private[this] def first(l: List[LuaValue]) = (l.headOption getOrElse LuaNil).wrapped

  override def call(): luaj.LuaValue = first(f(List.empty))

  override def call(a1: luaj.LuaValue) = first(f(List(LuaValue(a1))))

  override def call(a1: luaj.LuaValue, a2: luaj.LuaValue) = {
    first(f(List(LuaValue(a1), LuaValue(a2))))
  }

  override def call(a1: luaj.LuaValue, a2: luaj.LuaValue, a3: luaj.LuaValue) = {
    first(f(List(LuaValue(a1), LuaValue(a2), LuaValue(a3))))
  }

  override def invoke(vargs: luaj.Varargs) = {
    val l = (for (i <- 1 to vargs.narg) yield LuaValue(vargs.arg(i))).toList
    luaj.LuaValue.varargsOf(f(l).map(_.wrapped).toArray)
  }
}
