package Diameter

import Diameter.Coder.{Avp, AvpValueDiameterIdentity, UTFString}
import Diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 14/04/2016.
  */
case class AvpDiameterIdentity (flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:UTFString) extends Avp with AvpValueDiameterIdentity {
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}