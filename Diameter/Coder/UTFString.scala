package Diameter.Coder

import Util.Coder

/**
  * Created by edzmbuh on 08/04/2016.
  */
class UTFString(data:Either[Seq[Byte], String]) extends DiameterCoder.TypeCoder[String](data){
  val encode = DiameterCoder.encodeString _
  val decode = DiameterCoder.decodeString _
  def this(value:String) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {"UTF:\""+value+"\""+Coder.bytes2hexLen(dataRaw)}
}
