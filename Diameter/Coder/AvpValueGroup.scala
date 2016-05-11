package Diameter.Coder

import Diameter.Coder

/**
  * Created by edzmbuh on 14/04/2016.
  */
trait AvpValueGroup extends AvpValue{
  def value:Coder.Group
  def dataRaw = value.dataRaw
}
