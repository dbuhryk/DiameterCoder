package diameter.Dictionary

object DictionaryAvpTypeValue extends Enumeration {
  val UTF8String, IPAddress,DiameterIdentity,IPFilterRule,QoSFilterRule,MIPRegistrationRequest, VendorId, AppId, Enumerated,
  Float32, OctetString, DiameterURI, Integer32, Unsigned32, Integer64, Unsigned64, Time, Grouped, Unknown = Value
}