package diameter.Coder

import  diameter.ValueContainers.Integer32

trait AvpValueInteger32 extends AvpValue{
  def value:Integer32
  def dataRaw = value.dataRaw
  override def toString() = value.toString()
}
