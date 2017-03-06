package diameter.Coder

import diameter.ValueContainers.Integer64

trait AvpValueInteger64 extends AvpValue{
  def value:Integer64
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}