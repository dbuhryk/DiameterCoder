package diameter.ValueContainers

import diameter.Converter
import diameter.Coder.{DiameterCoder, TypeCoder}

/**
  * Created by edzmbuh on 08/04/2016.
  */
class Integer32(data:Either[Seq[Byte], Int]) extends TypeCoder[Int](data){
  val encode = DiameterCoder.encodeInteger32 _
  val decode = DiameterCoder.decodeInteger32 _
  def this(value:Int) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {"Int32:"+value+Converter.bytes2hexLen(dataRaw)}
}
