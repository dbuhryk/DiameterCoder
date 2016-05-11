package Diameter.Dictionary

object DictionaryAvpTypeValue extends Enumeration {
  val UTF8String, IPAddress,DiameterIdentity,IPFilterRule,QoSFilterRule,MIPRegistrationRequest, VendorId, AppId, Enumerated,
  Float32, OctetString, DiameterURI, Integer32, Unsigned32, Integer64, Unsigned64, Time, Grouped, Unknown = Value
}

object DictionaryAvpFlagValue extends Enumeration {
  val must, may, mustnot, shouldnot = Value
}

case class DictionaryAvpFlags(flagEncrypt:Boolean,
                              flagMadatory:DictionaryAvpFlagValue.Value,
                              flagProtected:DictionaryAvpFlagValue.Value,
                              flagVendorbit:DictionaryAvpFlagValue.Value) {
}

trait DictionaryObject
//case class GenericType(name:String, parent:Option[String]=None, description:Option[String]=None) extends DictionaryObject
//class DictionaryCommand(val code:Long, val name:String ) extends DictionaryObject
case class DictionaryCommand(code:Long, name:String ) extends DictionaryObject {
  override def toString() = {code +"["+name+"]"}
}
case class DictionaryApplication(val id:Long, val name:String) extends DictionaryObject {
  override def toString() = {id +"["+name+"]"}
}
//class DictionaryVendor(val code:Long, val vendorId:String, val name:Option[String]=None) extends DictionaryObject
case class DictionaryVendor(code:Long, vendorId:String, name:Option[String]=None) extends DictionaryObject {
  override def toString() = {code +"["+name.getOrElse("None")+"]"}
}
//class DictionaryAvp(val code:Long, val name:Option[String], val typeName:DictionaryAvpTypeValue.Value, val vendorid:Option[DictionaryVendor], val flags: DictionaryAvpFlags) extends DictionaryObject
case class DictionaryAvp(code:Long, name:Option[String], typeName:DictionaryAvpTypeValue.Value, vendorid:Option[DictionaryVendor], flags: DictionaryAvpFlags) extends DictionaryObject {
  override def toString() = {code +"["+name.getOrElse("None")+"]"}//+ vendorid.getOrElse("")}
}
trait DictionaryAvpEnum{
  val enum:Map[Long, String]
}
trait DictionaryAvpGroup{
  val group:Seq[DictionaryAvp]
}

