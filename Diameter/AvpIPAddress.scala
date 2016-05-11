package Diameter

import Diameter.Coder._
import Diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 28/04/2016.
  */
case class AvpIPAddress(flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:IPAddress) extends Avp with AvpValueIPAddress {
  override def toString() = {
    avp + " " + flags + " " + vendor.getOrElse("") + " " + value
  }
}
