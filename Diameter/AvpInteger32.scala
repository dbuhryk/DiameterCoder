package Diameter

import Diameter.Coder.{Avp, AvpValueInteger32, Integer32}
import Diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 11/04/2016.
  */
case class AvpInteger32(flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:Integer32) extends Avp with AvpValueInteger32{
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}

