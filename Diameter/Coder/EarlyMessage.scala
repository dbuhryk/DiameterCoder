package Diameter.Coder

import Diameter.CommandFlags

/**
  * Created by edzmbuh on 05/04/2016.
  */
trait EarlyMessage {
  val bodyRaw:Seq[Byte]
  def headerRaw:Seq[Byte] = bodyRaw.slice(0,20)
  def versionRaw:Seq[Byte] = headerRaw.slice(0,1)
  def version:Byte = versionRaw.foldLeft(0.toByte)((a,b)=> ((a << 8) + (b & 0xFF)).toByte)
  def lengthRaw:Seq[Byte] = headerRaw.slice(1,4)
  def length:Int = lengthRaw.foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF))
  def flagsRaw:Seq[Byte] = headerRaw.slice(4,5)
  def flags:CommandFlags = new CommandFlags(flagsRaw.foldLeft(0.toByte)((a, b)=> ((a << 8) + (b & 0xFF)).toByte))
  def cmdCodeRaw:Seq[Byte] = headerRaw.slice(5,8)
  def cmdCode:Int = cmdCodeRaw.foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF))
  def appIdRaw:Seq[Byte] = headerRaw.slice(8,12)
  def appId:Int = appIdRaw.foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF))
  def hbhRaw:Seq[Byte] = headerRaw.slice(12,16)
  def hbh:Int = hbhRaw.foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF))
  def eteRaw:Seq[Byte] = headerRaw.slice(16,20)
  def ete:Int = eteRaw.foldLeft(0.toInt)((a,b)=> (a << 8) + (b & 0xFF))
  def avpsRaw:Seq[Byte] = bodyRaw.slice(20,bodyRaw.length)
  def avps:Seq[EarlyAvp] = DiameterCoder.decodeEarlyAvpGroup(avpsRaw)
}
