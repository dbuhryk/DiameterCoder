package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueInteger64
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 06/03/2017.
  */
case class AvpInteger64(flags:AvpFlags, avp:DictionaryAvp, vendor:Option[DictionaryVendor], value:Integer64) extends Avp with AvpValueInteger64{
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
  }
