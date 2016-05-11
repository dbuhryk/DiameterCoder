package Diameter.Coder

import Diameter.Coder

/**
  * Created by edzmbuh on 11/04/2016.
  */
trait AvpValueInteger32 extends AvpValue{
  def value:Coder.Integer32
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}
