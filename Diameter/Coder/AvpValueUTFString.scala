package Diameter.Coder

import Diameter.Coder

/**
  * Created by edzmbuh on 11/04/2016.
  */
trait AvpValueUTFString extends AvpValue{
  def value:Coder.UTFString
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}
