package Diameter

import Diameter.Coder.{Avp, AvpValue, AvpValueGroup, Group}
import Diameter.Dictionary.{DictionaryAvp, DictionaryAvpGroup, DictionaryVendor, GenericDictionary}

/**
  * Created by edzmbuh on 14/04/2016.
  */
case class AvpGrouped (flags:AvpFlags, avp:DictionaryAvp with DictionaryAvpGroup, vendor:Option[DictionaryVendor], value:Group) extends Avp with AvpValueGroup {
  def this(_flags:AvpFlags, _avp:DictionaryAvp with DictionaryAvpGroup, _vendor:Option[DictionaryVendor], _list:Avp with AvpValue*)(implicit dictionary:GenericDictionary) = this(_flags,_avp,_vendor,new Group(_list.toList)(dictionary))
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}
