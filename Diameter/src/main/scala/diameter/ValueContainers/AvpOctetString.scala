package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueOctetString
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 19/12/2016.
  */
case class AvpOctetString (flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:OctetString) extends Avp with AvpValueOctetString {
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}
