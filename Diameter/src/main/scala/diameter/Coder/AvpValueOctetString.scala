package diameter.Coder

import diameter.ValueContainers.OctetString

/**
  * Created by edzmbuh on 19/12/2016.
  */
trait AvpValueOctetString extends AvpValue{
  def value:OctetString
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}
