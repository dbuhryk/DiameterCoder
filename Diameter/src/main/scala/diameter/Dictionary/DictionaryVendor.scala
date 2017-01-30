package diameter.Dictionary

//class DictionaryVendor(val code:Long, val vendorId:String, val name:Option[String]=None) extends DictionaryObject
case class DictionaryVendor(code:Long, vendorId:String, name:Option[String]=None) extends DictionaryObject {
  override def toString() = {code +"["+name.getOrElse("None")+"]"}
  def ==(that:DictionaryVendor):Boolean = {code == that.code}
}

object DictionaryVendor {
  def apply(code:Int)(implicit dictionary:GenericDictionary):Option[diameter.Dictionary.DictionaryVendor] = {
    dictionary.collect(
      { case x @ diameter.Dictionary.DictionaryVendor(_code, _, _) if _code == code => x } :PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryVendor]
    ).collectFirst({case x:diameter.Dictionary.DictionaryVendor => x})
  }
  def apply(name:String)(implicit dictionary:GenericDictionary):Option[diameter.Dictionary.DictionaryVendor] = {
    dictionary.collect(
      { case x @ diameter.Dictionary.DictionaryVendor(_, _id, _name) if _id== name || _name.contains(name) => x } :PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryVendor]
    ).collectFirst({case x:diameter.Dictionary.DictionaryVendor => x})
  }
}