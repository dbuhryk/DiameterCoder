package diameter.Dictionary

//class DictionaryCommand(val code:Long, val name:String ) extends DictionaryObject
case class DictionaryCommand(code:Long, name:String ) extends DictionaryObject {
  override def toString() = {code +"["+name+"]"}
  def ==(that:DictionaryCommand):Boolean = {code == that.code}
}

object DictionaryCommand {
  def apply(code:Int)(implicit dictionary:GenericDictionary):diameter.Dictionary.DictionaryCommand = {
    dictionary.collect(
      { case x:diameter.Dictionary.DictionaryCommand if x.code == code => x }:PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryCommand]
    ).collectFirst({case x:diameter.Dictionary.DictionaryCommand => x}).get
  }
  def apply(name:String)(implicit dictionary:GenericDictionary):diameter.Dictionary.DictionaryCommand = {
    dictionary.collect(
      { case x:diameter.Dictionary.DictionaryCommand if x.name == name => x }:PartialFunction[DictionaryObject,diameter.Dictionary.DictionaryCommand]
    ).collectFirst({case x:diameter.Dictionary.DictionaryCommand => x}).get
  }
}