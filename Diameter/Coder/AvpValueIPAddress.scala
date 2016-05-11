package Diameter.Coder

import Diameter.Coder

/**
  * Created by edzmbuh on 28/04/2016.
  */
trait AvpValueIPAddress extends AvpValue{
  def value:Coder.IPAddress
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}
