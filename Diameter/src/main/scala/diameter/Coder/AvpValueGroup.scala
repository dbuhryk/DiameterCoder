package diameter.Coder

import  diameter.ValueContainers.Group

/**
  * Created by edzmbuh on 14/04/2016.
  */
trait AvpValueGroup extends AvpValue{
  def value:Group
  def dataRaw = value.dataRaw
}
