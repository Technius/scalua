package co.technius.scalua

import java.io.{InputStream, PrintStream, StringReader}
import org.luaj.{vm2 => luaj}
import org.luaj.vm2.lib.jse.JsePlatform
import scala.io.Source
import scala.language.implicitConversions

class Globals(override val wrapped: luaj.Globals) extends LuaValue(wrapped) {
  def stdin: InputStream = wrapped.STDIN
  def stdout: PrintStream = wrapped.STDOUT
  def stderr: PrintStream = wrapped.STDERR

  def stdin_=(value: InputStream): Unit = wrapped.STDIN = value
  def stdout_=(value: PrintStream): Unit = wrapped.STDOUT = value
  def stderr_=(value: PrintStream): Unit = wrapped.STDERR = value

  def load(source: Source, chunkName: String): LuaValue = {
    val lines = source.getLines.mkString("\n")
    source.close()
    LuaValue(wrapped.load(new StringReader(lines), chunkName))
  }

  def load(script: String): LuaValue = LuaValue(wrapped.load(script))

  def load(script: String, chunkName: String): LuaValue = LuaValue(wrapped.load(script, chunkName))

  def loadFile(fileName: String): LuaValue = LuaValue(wrapped.loadfile(fileName))
}

object Globals {
  def standard(): Globals = JsePlatform.standardGlobals()
  def debug(): Globals = JsePlatform.debugGlobals()

  implicit def fromLuaj(g: luaj.Globals): Globals = new Globals(g)
}
