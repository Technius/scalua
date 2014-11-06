# Scalua

![Build Status](https://travis-ci.org/Technius/scalua.svg?branch=master)

Scalua is a wrapper around [LuaJ 3.0](http://luaj.org/luaj.html), written in Scala.

Scalua is currently a work-in-progress.

## Features

* Operators
  - Addition: `LuaInt(5) + LuaInt(3)`
  - Subtraction: `LuaInt(3) - LuaInt(10)`
  - Multiplication: `LuaInt(2) * LuaInt(3)`
  - Division: `LuaInt(50) / LuaInt(2)`
  - Negate: `-LuaInt(1)`
  - Equality: `LuaInt(1) == LuaInt(1)`
  - Boolean operators: `LuaBoolean(true) && LuaBoolean(true) || LuaBoolean(false)`
  - Comparison: `LuaInt(5) > LuaInt(3)`
  - Unary not: `~LuaBoolean(true)`
* Interoperability with LuaJ
* Pattern matching on `LuaValue`s
* Simple table manipulation
  - Getting: `myTable("key")`
  - Setting: `myTable("key") = "value"`
* Simple creation of Scala functions to be exposed in Lua:
  ```
  val helloWorld = LuaFunction {
    case LuaString("hello world") :: List() => {
      List("Argument was 'hello world'")
    }
    case LuaString("hello") :: List() => { 
      List("This supports", "multiple return values")
    }
    case List() => List("No args")
  }
  ```

### License

Scalua is licensed under the MIT License. See LICENSE for more details.
