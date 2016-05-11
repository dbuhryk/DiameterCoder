package Diameter.Coder

import Util.Coder

/**
  * Created by edzmbuh on 08/04/2016.
  */
class Integer32(data:Either[Seq[Byte], Int]) extends DiameterCoder.TypeCoder[Int](data){
  val encode = DiameterCoder.encodeInteger32 _
  val decode = DiameterCoder.decodeInteger32 _
  def this(value:Int) = this(Right(value))
  def this(seq:Seq[Byte]) = this(Left(seq))
  override def toString() = {"Int32:"+value+Coder.bytes2hexLen(dataRaw)}
}
