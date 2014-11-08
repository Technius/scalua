package co.technius.scalua

import org.luaj.{vm2 => luaj}

abstract class LuaValue protected(_wrapped: luaj.LuaValue) {
  def wrapped = _wrapped

  def +(rhs: Double): LuaValue = LuaValue(_wrapped add rhs)

  def +(rhs: Int): LuaValue = LuaValue(_wrapped add rhs)

  def +(rhs: LuaValue): LuaValue = LuaValue(_wrapped add rhs.wrapped)

  def -(rhs: Double): LuaValue = LuaValue(_wrapped sub rhs)

  def -(rhs: Int): LuaValue = LuaValue(_wrapped sub rhs)

  def -(rhs: LuaValue): LuaValue = LuaValue(_wrapped add rhs.wrapped)

  def *(rhs: Double): LuaValue = LuaValue(_wrapped mul rhs)

  def *(rhs: Int): LuaValue = LuaValue(_wrapped mul rhs)

  def *(rhs: LuaValue): LuaValue = LuaValue(_wrapped mul rhs.wrapped)

  def /(rhs: Double): LuaValue = LuaValue(_wrapped div rhs)

  def /(rhs: Int): LuaValue = LuaValue(_wrapped div rhs)

  def /(rhs: LuaValue): LuaValue = LuaValue(_wrapped div rhs.wrapped)

  def ^(rhs: Double): LuaValue = LuaValue(_wrapped pow rhs)

  def ^(rhs: Int): LuaValue = LuaValue(_wrapped pow rhs)

  def ^(rhs: LuaValue): LuaValue = LuaValue(_wrapped pow rhs.wrapped)

  def &&(rhs: LuaValue): LuaValue = LuaValue(_wrapped and rhs.wrapped)

  def ||(rhs: LuaValue): LuaValue = LuaValue(_wrapped or rhs.wrapped)

  def ==(rhs: LuaValue): LuaValue = LuaValue(_wrapped eq rhs.wrapped)

  def >(rhs: Int): LuaValue = LuaValue(_wrapped gt rhs)

  def >(rhs: Double): LuaValue = LuaValue(_wrapped gt rhs)

  def >(rhs: LuaValue): LuaValue = LuaValue(_wrapped gt rhs.wrapped)

  def <(rhs: Int): LuaValue = LuaValue(_wrapped lt rhs)

  def <(rhs: Double): LuaValue = LuaValue(_wrapped lt rhs)

  def <(rhs: LuaValue): LuaValue = LuaValue(_wrapped lt rhs.wrapped)

  def >=(rhs: Int): LuaValue = LuaValue(_wrapped gteq rhs)

  def >=(rhs: Double): LuaValue = LuaValue(_wrapped gteq rhs)

  def >=(rhs: LuaValue): LuaValue = LuaValue(_wrapped gteq rhs.wrapped)

  def <=(rhs: Int): LuaValue = LuaValue(_wrapped lteq rhs)

  def <=(rhs: Double): LuaValue = LuaValue(_wrapped lteq rhs)

  def <=(rhs: LuaValue): LuaValue = LuaValue(_wrapped lteq rhs.wrapped)

  def apply(key: Int): LuaValue = LuaValue(_wrapped.get(key))

  def apply(key: LuaValue): LuaValue = LuaValue(_wrapped.get(key.wrapped))

  def apply(key: String): LuaValue = LuaValue(_wrapped.get(key))

  def update(key: Int, value: LuaValue): Unit = _wrapped.set(key, value.wrapped)

  def update(key: Int, value: String): Unit = _wrapped.set(key, value)

  def update(key: String, value: LuaValue): Unit = _wrapped.set(key, value.wrapped)

  def update(key: String, value: Int): Unit = _wrapped.set(key, value)
  
  def update(key: String, value: Double): Unit = _wrapped.set(key, value)
 
  def update(key: String, value: String): Unit = _wrapped.set(key, value)

  def arg(index: Int): LuaValue = LuaValue(_wrapped.arg(index))

  def arg1: LuaValue = LuaValue(_wrapped.arg1)

  def len: LuaValue = LuaValue(_wrapped.len)

  def length: Int = _wrapped.length

  def unary_~(): LuaValue = LuaValue(_wrapped.not)

  def unary_-(): LuaValue = LuaValue(_wrapped.neg)

  def metatable: LuaValue = LuaValue(_wrapped.getmetatable)

  def metatable_=(value: LuaValue): LuaValue = LuaValue(_wrapped.setmetatable(value.wrapped))

  def call(args: LuaValue*): LuaValue = {
    val vargs = luaj.LuaValue.varargsOf(args.map(_.wrapped).toArray)
    val ret = _wrapped.invoke(vargs)
    if (ret.narg > 0) LuaValue(ret.arg(1)) else LuaNil
  }

  def callFunction(name: String, args: LuaValue*): LuaValue = {
    this(name).call(args: _*)
  }

  def callMethod(name: String, args: LuaValue*): LuaValue = {
    this(name).call(this :: args.toList: _*)
  }

  def invoke(args: LuaValue*): List[LuaValue] = {
    val vargs = luaj.LuaValue.varargsOf(args.map(_.wrapped).toArray)
    val ret = _wrapped.invoke(vargs)
    if (ret.narg == 0) {
      List(LuaNil)
    } else {
      (for (i <- 1 to ret.narg) yield LuaValue(ret.arg(i))).toList
    }
  }

  def invokeFunction(name: String, args: LuaValue*): List[LuaValue] = {
    this(name).invoke(args: _*)
  }

  def invokeMethod(name: String, args: LuaValue*): List[LuaValue] = {
    invokeFunction(name, this :: args.toList: _*)
  }

  override def toString: String = _wrapped.toString

  override def equals(other: Any): Boolean = other match {
    case l: LuaValue => _wrapped.equals(l.wrapped)
    case _ => _wrapped.equals(other)
  }

  override def hashCode: Int = _wrapped.hashCode
}
case object LuaNil extends LuaValue(luaj.LuaValue.NIL)

object LuaValue {
  def apply(value: luaj.LuaValue): LuaValue = value match {
    case l: luaj.LuaInteger => new LuaInt(l)
    case l: luaj.LuaDouble => new LuaDouble(l)
    case l: luaj.LuaBoolean => new LuaBoolean(l)
    case l: luaj.LuaString => new LuaString(l)
    case l: luaj.LuaFunction => new LuaFunction(l)
    case l: luaj.LuaTable => new LuaTable(l)
    case l: luaj.LuaUserdata => new LuaUserdata(l)
    case l: luaj.LuaNil => LuaNil
  }

  val Nil = LuaNil
}
