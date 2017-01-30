package diameter.ValueContainers

import diameter.Converter
import diameter.Coder.{DiameterCoder, TypeCoder}

/**
  * Created by edzmbuh on 19/12/2016.
  */
class OctetString(data:Either[Seq[Byte], String]) extends TypeCoder[String](data){
  val encode = DiameterCoder.encodeOctetString _
  val decode = DiameterCoder.decodeOctetString _
  def this(value:String) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {Converter.bytes2hexLen(dataRaw)}
}
