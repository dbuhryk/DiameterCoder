package Diameter.Coder

import Diameter.Dictionary.{DictionaryAvp, DictionaryVendor}
import Diameter.{AvpFlags, AvpInteger32}

/**
  * Created by edzmbuh on 05/04/2016.
  */
trait Avp{
  val flags:AvpFlags
  val vendor:Option[DictionaryVendor]
  val avp:DictionaryAvp
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("")}
}

object Avp{
  def AvpInteger32(flags:AvpFlags,avp:DictionaryAvp,vendor:Option[DictionaryVendor])(value:Integer32) = new AvpInteger32(flags,avp,vendor,value)
}
