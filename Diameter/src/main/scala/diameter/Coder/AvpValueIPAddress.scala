package diameter.Coder

import diameter.ValueContainers.IPAddress

/**
  * Created by edzmbuh on 28/04/2016.
  */
trait AvpValueIPAddress extends AvpValue{
  def value:IPAddress
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}
