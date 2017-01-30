package diameter.ValueContainers

import diameter.Converter
import diameter.Coder.{DiameterCoder, TypeCoder}

class UTFString(data:Either[Seq[Byte], String]) extends TypeCoder[String](data){
  val encode = DiameterCoder.encodeString _
  val decode = DiameterCoder.decodeString _
  def this(value:String) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {"UTF:\""+value+"\""+Converter.bytes2hexLen(dataRaw)}
}
