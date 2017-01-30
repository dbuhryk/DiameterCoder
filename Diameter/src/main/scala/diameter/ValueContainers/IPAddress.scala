package diameter.ValueContainers

import java.net.InetAddress

import diameter.Converter
import diameter.Coder.{DiameterCoder, TypeCoder}

/**
  * Created by edzmbuh on 27/04/2016.
  */
class IPAddress (data:Either[Seq[Byte], InetAddress]) extends TypeCoder[InetAddress](data){
  val encode = DiameterCoder.encodeIPAddress _
  val decode = DiameterCoder.decodeIPAddress _
  def this(value:InetAddress) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {"IP:"+value+Converter.bytes2hexLen(dataRaw)}
}