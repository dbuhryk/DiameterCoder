package Diameter

import Diameter.Coder.{Avp, AvpValueVendorId, Integer32}
import Diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 14/04/2016.
  */
case class AvpVendorId (flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:Integer32) extends Avp with AvpValueVendorId
