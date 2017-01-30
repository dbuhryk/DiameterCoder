package diameter.ValueContainers

import diameter.Converter
import diameter.Coder.{DiameterCoder, TypeCoder}
import diameter.Dictionary.GenericDictionary

/**
  * Created by edzmbuh on 14/04/2016.
  */
class Group(data:Either[Seq[Byte], List[Avp]])(implicit dictionary:GenericDictionary) extends TypeCoder[List[Avp]](data) {
  val encode = DiameterCoder.encodeAvpGroup _
  val decode = (x:Seq[Byte])=> DiameterCoder.decodeEarlyAvpGroup(x).map( DiameterCoder.decodeAvp ).toList
  def this(value:List[Avp])(implicit dictionary:GenericDictionary) = this(Right(value))
  //def this(value:Avp *)(implicit dictionary:GenericDictionary) = this(Right(value.toList))
  def this(seq:Seq[Byte])(implicit dictionary:GenericDictionary) = this(Left(seq))
  override def toString() = {"{"+value.map(_.toString()).mkString(",")+"}"+Converter.bytes2hexLen(dataRaw)}
}