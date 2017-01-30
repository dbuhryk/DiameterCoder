package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueUTFString
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

case class AvpUTFString(flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:UTFString) extends Avp with AvpValueUTFString {
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}
