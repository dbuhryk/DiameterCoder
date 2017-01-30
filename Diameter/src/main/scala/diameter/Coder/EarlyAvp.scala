package diameter.Coder

import  diameter.AvpFlags

/**
  * Created by edzmbuh on 05/04/2016.
  */
trait EarlyAvp {
  val bodyRaw:Seq[Byte]
  def earlyHeaderRaw:Seq[Byte] = bodyRaw.slice(0,8)
  def headerRaw:Seq[Byte] = if (flags.isVendorSpecific) bodyRaw.slice(0,12) else bodyRaw.slice(0,8)
  def codeRaw:Seq[Byte] = earlyHeaderRaw.slice(0,4)
  def code:Int = codeRaw.foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF))
  def flagsRaw:Seq[Byte] = earlyHeaderRaw.slice(4,5)
  def flags:AvpFlags = new AvpFlags(flagsRaw.foldLeft(0.toByte)((a,b)=> ((a << 8) + (b & 0xFF)).toByte))
  def lengthRaw:Seq[Byte] = earlyHeaderRaw.slice(5,8)
  def length:Int = lengthRaw.foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF))
  def vendorIdRaw:Option[Seq[Byte]] = if (flags.isVendorSpecific) Some(headerRaw.slice(8,12)) else None
  def vendorId:Option[Int] = if (flags.isVendorSpecific) Some(vendorIdRaw.get.foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF))) else None
  def dataRaw:Seq[Byte] = if (flags.isVendorSpecific) bodyRaw.slice(12,length) else bodyRaw.slice(8,length)
}