package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueUnsigned64
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

case class AvpUnsigned64 (flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:Integer64) extends Avp with AvpValueUnsigned64