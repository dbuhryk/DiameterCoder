package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueVendorId
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

case class AvpVendorId (flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor], value:Integer32) extends Avp with AvpValueVendorId
