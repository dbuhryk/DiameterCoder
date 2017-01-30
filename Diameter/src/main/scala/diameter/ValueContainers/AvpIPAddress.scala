package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueIPAddress
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

case class AvpIPAddress(flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:IPAddress) extends Avp with AvpValueIPAddress {
  override def toString() = {
    avp + " " + flags + " " + vendor.getOrElse("") + " " + value
  }
}
