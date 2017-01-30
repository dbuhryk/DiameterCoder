package diameter.Dictionary

//class DictionaryAvp(val code:Long, val name:Option[String], val typeName:DictionaryAvpTypeValue.Value, val vendorid:Option[DictionaryVendor], val flags: DictionaryAvpFlags) extends DictionaryObject
case class DictionaryAvp(code:Long, name:Option[String], typeName:DictionaryAvpTypeValue.Value, vendorid:Option[DictionaryVendor], flags: DictionaryAvpFlags) extends DictionaryObject {
  override def toString() = {code +"["+name.getOrElse("None")+"]"}//+ vendorid.getOrElse("")}
  def ==(that:DictionaryAvp):Boolean = {code == that.code && vendorid == that.vendorid}
}

object DictionaryAvp {
  def apply(id:Int)(implicit dictionary:GenericDictionary):diameter.Dictionary.DictionaryAvp = {
    dictionary.collect(
      { case x @ diameter.Dictionary.DictionaryAvp(_id, _, _, None, _) if _id == id => x } :PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryAvp]
    ).collectFirst({case x:diameter.Dictionary.DictionaryAvp => x}).get
  }
  def apply(id:Int, vendor:Int)(implicit dictionary:GenericDictionary):diameter.Dictionary.DictionaryAvp = {
    dictionary.collect(
      { case x @ diameter.Dictionary.DictionaryAvp(_id, _, _, _vendor, _) if _id == id && ( (_vendor.isDefined && _vendor.get.code == vendor ) ) => x } :PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryAvp]
    ).collectFirst({case x:diameter.Dictionary.DictionaryAvp => x}).get
  }
  def apply(name:String)(implicit dictionary:GenericDictionary):diameter.Dictionary.DictionaryAvp = {
    dictionary.collect(
      { case x @ diameter.Dictionary.DictionaryAvp(_, _name, _, None, _) if _name.contains(name) => x } :PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryAvp]
    ).collectFirst({case x:diameter.Dictionary.DictionaryAvp => x}).get
  }
  def apply(name:String, vendor:Int)(implicit dictionary:GenericDictionary):diameter.Dictionary.DictionaryAvp = {
    dictionary.collect(
      { case x @ diameter.Dictionary.DictionaryAvp(_, _name, _, _vendor, _) if _name.contains(name) && (_vendor.isDefined && _vendor.get.code == vendor ) => x } :PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryAvp]
    ).collectFirst({case x:diameter.Dictionary.DictionaryAvp => x}).getOrElse(throw new Exception("Not found AVP(name:"+name+",vendor:"+vendor+")"))
  }
}