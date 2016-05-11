package Diameter

/**
  * Created by edzmbuh on 05/04/2016.
  */

object CommandFlags extends Enumeration {
  val Request,Proxiable, Error,Retransmission, Reserved = Value
}

class CommandFlags(val value:Byte){
  def this(s:Seq[CommandFlags.Value]) = this(s.foldLeft(0.toByte)(
    (x,flag) => (x | (flag match {
      case CommandFlags.Request => 0x80.toByte
      case CommandFlags.Proxiable => 0x40.toByte
      case CommandFlags.Error => 0x20.toByte
      case CommandFlags.Retransmission => 0x10.toByte
      case _ => 0
    }).toByte).toByte))
  override def toString():String = {
    "0x"+Util.Coder.bytes2hex(Seq(value))+"["+(if ((value & 0x80) != 0) "R" else "") +
      (if ((value & 0x40) != 0) "P" else "") +
      (if ((value & 0x20) != 0) "E" else "") +
      (if ((value & 0x10) != 0) "T" else "")+"]"
  }
  def contains(flags:CommandFlags):Boolean = {
    (value & flags.value) == flags.value
  }
  def + (cf: CommandFlags):CommandFlags = {
    new CommandFlags( (value | cf.value).toByte)
  }
  def - (cf: CommandFlags):CommandFlags = {
    new CommandFlags( (value & ~cf.value).toByte)
  }
  def + (cf: CommandFlags.Value):CommandFlags = {
    new CommandFlags( (value | (new CommandFlags(value)).value ).toByte )
  }
  def - (cf: CommandFlags.Value):CommandFlags = {
    new CommandFlags( (value & ~(new CommandFlags(value)).value ).toByte )
  }
  def isRequest:Boolean = {(value & 0x80) != 0}
  def isProxiable:Boolean = {(value & 0x40) != 0}
  def isError:Boolean = {(value & 0x20) != 0}
  def isRetransmission:Boolean = {(value & 0x10) != 0}
}
