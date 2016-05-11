package Diameter

import Diameter.Coder.{Avp, AvpValueEnumerated, Integer32}
import Diameter.Dictionary.{DictionaryAvp, DictionaryAvpEnum, DictionaryVendor}

/**
  * Created by edzmbuh on 14/04/2016.
  */
case class AvpEnumerated (flags:AvpFlags,avp:DictionaryAvp with DictionaryAvpEnum,vendor:Option[DictionaryVendor], value:Integer32) extends Avp with AvpValueEnumerated {
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}