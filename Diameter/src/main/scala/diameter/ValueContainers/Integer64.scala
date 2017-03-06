package diameter.ValueContainers

import diameter.Coder.{DiameterCoder, TypeCoder}
import diameter.Converter

class Integer64 (data:Either[Seq[Byte], Long]) extends TypeCoder[Long](data){
  val encode = DiameterCoder.encodeInteger64 _
  val decode = DiameterCoder.decodeInteger64 _
  def this(value:Long) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {"Int64:"+value+Converter.bytes2hexLen(dataRaw)}
}
