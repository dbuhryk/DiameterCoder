package Diameter.Coder

import java.net.InetAddress

import Util.Coder

/**
  * Created by edzmbuh on 27/04/2016.
  */
class IPAddress (data:Either[Seq[Byte], InetAddress]) extends DiameterCoder.TypeCoder[InetAddress](data){
  val encode = DiameterCoder.encodeIPAddress _
  val decode = DiameterCoder.decodeIPAddress _
  def this(value:InetAddress) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {"IP:"+value+Coder.bytes2hexLen(dataRaw)}
}