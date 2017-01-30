package diameter.Coder

import  diameter.ValueContainers.UTFString

/**
  * Created by edzmbuh on 11/04/2016.
  */
trait AvpValueUTFString extends AvpValue{
  def value:UTFString
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}
