package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueEnumerated
import diameter.Dictionary.{DictionaryAvp, DictionaryAvpEnum, DictionaryVendor}

case class AvpEnumerated (flags:AvpFlags,avp:DictionaryAvp with DictionaryAvpEnum,vendor:Option[DictionaryVendor], value:Integer32) extends Avp with AvpValueEnumerated {
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}