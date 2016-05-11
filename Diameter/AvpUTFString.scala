package Diameter

import Diameter.Coder.{Avp, AvpValueUTFString, UTFString}
import Diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 11/04/2016.
  */
case class AvpUTFString(flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:UTFString) extends Avp with AvpValueUTFString {
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}
