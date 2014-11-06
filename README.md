# Scalua

Scalua is a wrapper around [LuaJ 3.0](http://luaj.org/luaj.html), written in Scala.

Scalua is currently a work-in-progress.

## Features

* Operators
  - Addition: `LuaInt(5) + LuaInt(3)`
  - Subtraction: `LuaInt(3) - LuaInt(10)`
  - Multiplication: `LuaInt(2) * LuaInt(3)`
  - Division: `LuaInt(50) / LuaInt(2)`
  - Equality: `LuaInt(1) == LuaInt(1)`
  - Comparison: `LuaInt(5) > LuaInt(3)`
  - Unary not: `~LuaBoolean(true)`
* Interoperability with LuaJ
* Pattern matching on `LuaValue`s

### License

Scalua is licensed under the MIT License. See LICENSE for more details.
