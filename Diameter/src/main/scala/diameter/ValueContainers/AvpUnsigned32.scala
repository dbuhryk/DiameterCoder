package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueUnsigned32
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

case class AvpUnsigned32 (flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:Integer32) extends Avp with AvpValueUnsigned32
