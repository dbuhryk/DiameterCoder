package diameter.Dictionary

case class DictionaryApplication(val id:Long, val name:String) extends DictionaryObject {
  override def toString() = {id +"["+name+"]"}
  def ==(that:DictionaryApplication):Boolean = {id == that.id}
}

object DictionaryApplication {
  def apply(id:Int)(implicit dictionary:GenericDictionary):DictionaryApplication = {
    dictionary.collectFirst(
      { case x@diameter.Dictionary.DictionaryApplication(_id, _) if _id == id.toLong => x }:PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryApplication]
    ).get
  }
  def apply(name:String)(implicit dictionary:GenericDictionary):DictionaryApplication = {
    dictionary.collectFirst(
      { case x@diameter.Dictionary.DictionaryApplication(_, _name) if _name == name => x }:PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryApplication]
    ).get
  }
}