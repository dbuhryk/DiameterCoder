package diameter.Coder

import diameter.Converter

/**
  * Created by edzmbuh on 11/04/2016.
  */
trait AvpValue{
  def dataRaw:Seq[Byte]
  def == (that:AvpValue) = {dataRaw == that.dataRaw}
  override def toString() = {Converter.bytes2hexLen(dataRaw)}
}
