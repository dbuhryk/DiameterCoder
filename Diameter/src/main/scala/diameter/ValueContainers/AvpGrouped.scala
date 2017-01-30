package diameter.ValueContainers

import diameter.AvpFlags
import diameter.Coder.AvpValueGroup
import diameter.Dictionary.{DictionaryAvp, DictionaryAvpGroup, DictionaryVendor, GenericDictionary}

case class AvpGrouped (flags:AvpFlags, avp:DictionaryAvp with DictionaryAvpGroup, vendor:Option[DictionaryVendor], value:Group) extends Avp with AvpValueGroup {
  def this(_flags:AvpFlags, _avp:DictionaryAvp with DictionaryAvpGroup, _vendor:Option[DictionaryVendor], _list:Avp*)(implicit dictionary:GenericDictionary) = this(_flags,_avp,_vendor,new Group(_list.toList)(dictionary))
  override def toString() = {avp + " "+flags+" "+vendor.getOrElse("") + " "+value}
}
