package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Dictionary.{DictionaryAvp, DictionaryVendor}

/**
  * Created by edzmbuh on 05/04/2016.
  */
trait AvpHeader{
  val flags:AvpFlags
  val vendor:Option[DictionaryVendor]
  val avp:DictionaryAvp
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("")}
  def ==(that:AvpHeader):Boolean = {avp == that.avp && vendor == that.vendor}
}
/*

object AvpHeader{
  def AvpInteger32(flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor])(value:Integer32) = new AvpInteger32(flags,avp,vendor,value)
}
*/
