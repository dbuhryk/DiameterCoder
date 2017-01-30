package diameter

object AvpFlags extends Enumeration {
  val VendorSpecific, Mandatory, Protected, Reserved = Value
  def apply(value:Byte) = new AvpFlags(value)
  //def apply(v:Int) = new AvpFlags(v.toByte)
  def apply(s:Seq[AvpFlags.Value]) = new AvpFlags(s)
}
class AvpFlags(val value:Byte) {
  def this(s:Seq[AvpFlags.Value]) = this(s.foldLeft(0.toByte)(
    (x,flag) => (x | (flag match {
      case AvpFlags.VendorSpecific => 0x80
      case AvpFlags.Mandatory => 0x40
      case AvpFlags.Protected => 0x20
      case _ => 0
    }).toByte).toByte))
  override def toString():String = {
    "0x"+Converter.bytes2hex(Seq(value))+"["+(if ((value & 0x80) != 0) "V" else "") +
      (if ((value & 0x40) != 0) "M" else "") +
      (if ((value & 0x20) != 0) "P" else "")+"]"
  }
  def contains(flags:AvpFlags):Boolean = {
    (value & flags.value) == flags.value
  }
  def isVendorSpecific:Boolean = {(value & 0x80) != 0}
  def isMandatory:Boolean = {(value & 0x40) != 0}
  def isProtected:Boolean = {(value & 0x20) != 0}
}
