package co.technius.scalua

trait LuaConverter[T] {
  def toJava(value: LuaValue): Option[T] = None
  def toLua(value: T): LuaValue = ???
}
