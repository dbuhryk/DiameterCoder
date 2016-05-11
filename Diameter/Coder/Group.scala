package Diameter.Coder

import Diameter.Dictionary.GenericDictionary
import Util.Coder

/**
  * Created by edzmbuh on 14/04/2016.
  */
class Group(data:Either[Seq[Byte], List[Avp with AvpValue]])(implicit dictionary:GenericDictionary) extends DiameterCoder.TypeCoder[List[Avp with AvpValue]](data) {
  val encode = DiameterCoder.encodeAvpGroup _
  val decode = (x:Seq[Byte])=> DiameterCoder.decodeEarlyAvpGroup(x).map( DiameterCoder.decodeAvp ).toList
  def this(value:List[Avp with AvpValue])(implicit dictionary:GenericDictionary) = this(Right(value))
  //def this(value:Avp with AvpValue *)(implicit dictionary:GenericDictionary) = this(Right(value.toList))
  def this(seq:Seq[Byte])(implicit dictionary:GenericDictionary) = this(Left(seq))
  override def toString() = {"{"+value.map(_.toString())+"}"+Coder.bytes2hexLen(dataRaw)}
}