package Diameter

import Diameter.Coder.{Avp, AvpValueUnsigned32, Integer32}
import Diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 14/04/2016.
  */
case class AvpUnsigned32 (flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:Integer32) extends Avp with AvpValueUnsigned32
