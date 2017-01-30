package diameter.ValueContainers

import diameter.Coder.AvpValue
import diameter.Converter

/**
  * Created by edzmbuh on 02/06/2016.
  */
trait Avp extends AvpHeader with AvpValue{
  def == (that:Avp) = {avp == that.avp && vendor == that.vendor && dataRaw == that.dataRaw}
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+Converter.bytes2hexLen(dataRaw)}
}
