package Diameter.Templates

import Diameter.Dictionary._

/**
  * Created by edzmbuh on 15/04/2016.
  */
object Dictionary {
  //implicit val dictionary:GenericDictionary
  def findVendorFirst(code:Int)(implicit dictionary:GenericDictionary):Option[DictionaryVendor] = {
    dictionary.collect(
      { case x @ DictionaryVendor(_code, _, _) if _code == code => x } :PartialFunction[DictionaryObject,DictionaryVendor]
    ).collectFirst({case x:DictionaryVendor => x})
  }
  def findVendorFirst(name:String)(implicit dictionary:GenericDictionary):Option[DictionaryVendor] = {
    dictionary.collect(
      { case x @ DictionaryVendor(_, _id, _name) if _id.contains(name) || _name.contains(name) => x } :PartialFunction[DictionaryObject,DictionaryVendor]
    ).collectFirst({case x:DictionaryVendor => x})
  }
  def findAvpFirst(id:Int)(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x @ DictionaryAvp(_id, _, _, None, _) if _id == id => x } :PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def findAvpFirst(id:Int, vendor:Int)(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x @ DictionaryAvp(_id, _, _, _vendor, _) if _id == id && ( (_vendor.isDefined && _vendor.get.code == vendor ) ) => x } :PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def findAvpFirst(name:String)(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x @ DictionaryAvp(_, _name, _, None, _) if _name.contains(name) => x } :PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def findAvpFirst(name:String, vendor:Int)(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x @ DictionaryAvp(_, _name, _, _vendor, _) if _name.contains(name) && (_vendor.isDefined && _vendor.get.code == vendor ) => x } :PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Result_Code()(implicit dictionary:GenericDictionary):Option[DictionaryAvp with DictionaryAvpEnum] = {
    dictionary.collect(
      { case x @ DictionaryAvp(268L, Some("Result-Code"), _, _, _) => x } :PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp with DictionaryAvpEnum => x})
  }
  def AVP_Destination_Host()(implicit dictionary:GenericDictionary):Option[DictionaryAvp ] = {
    dictionary.collect(
      { case x@DictionaryAvp(293L, Some("Destination-Host"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Destination_Realm()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect (
      { case x@DictionaryAvp(283L, Some("Destination-Realm"), _, _, _) => x } : PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Origin_Realm()(implicit dictionary:GenericDictionary):Option[DictionaryAvp ] = {
    dictionary.collect(
      { case x@DictionaryAvp(296L, Some("Origin-Realm"), _, _, _) => x } :PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Origin_Host()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(264L, Some("Origin-Host"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Session_Id()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(263L, Some("Session-Id"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Host_IP_Address()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(257L, Some("Host-IP-Address"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Product_Name()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(269L, Some("Product-Name"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Vendor_Id()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(266L, Some("Vendor-Id"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Supported_Vendor_Id()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(265L, Some("Supported-Vendor-Id"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Auth_Application_Id()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(258L, Some("Auth-Application-Id"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Acct_Application_Id()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(259L, Some("Acct-Application-Id"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def AVP_Vendor_Specific_Application_Id()(implicit dictionary:GenericDictionary):Option[DictionaryAvp] = {
    dictionary.collect(
      { case x@DictionaryAvp(260L, Some("Vendor-Specific-Application-Id"), _, _, _) => x }:PartialFunction[DictionaryObject,DictionaryAvp]
    ).collectFirst({case x:DictionaryAvp => x})
  }
  def CMD_Device_Watchdog()(implicit dictionary:GenericDictionary):Option[DictionaryCommand] = {
    dictionary.collect(
      { case x@DictionaryCommand(280L, "Device-Watchdog") => x }:PartialFunction[DictionaryObject,DictionaryCommand]
    ).collectFirst({case x:DictionaryCommand => x})
  }
  def CMD_Capabilities_Exchange()(implicit dictionary:GenericDictionary) = {
    dictionary.collectFirst(
      { case x@DictionaryCommand(257L, "Capabilities-Exchange") => x }:PartialFunction[DictionaryObject,DictionaryCommand]
    )
  }
  def CMD_Credit_Control()(implicit dictionary:GenericDictionary) = {
    dictionary.collectFirst(
      { case x@DictionaryCommand(272L, "Credit-Control") => x }:PartialFunction[DictionaryObject,DictionaryCommand]
    )
  }
  def APP_DiameterCommonMessages()(implicit dictionary:GenericDictionary) = {
    dictionary.collectFirst(
      { case x@DictionaryApplication(0L, "Diameter Common Messages") => x }:PartialFunction[DictionaryObject,DictionaryApplication]
    )
  }
  def APP_DiameterBaseAccounting()(implicit dictionary:GenericDictionary) = {
    dictionary.collectFirst(
      { case x@DictionaryApplication(3L, "Diameter Base Accounting") => x }:PartialFunction[DictionaryObject,DictionaryApplication]
    )
  }
  def APP_DiameterCreditControl()(implicit dictionary:GenericDictionary) = {
    dictionary.collectFirst(
      { case x@DictionaryApplication(4L, "Diameter Credit Control Application") => x }:PartialFunction[DictionaryObject,DictionaryApplication]
    )
  }
  def APP_Relay()(implicit dictionary:GenericDictionary) = {
    dictionary.collectFirst(
      { case x@DictionaryApplication(4294967295L, "Relay") => x }:PartialFunction[DictionaryObject,DictionaryApplication]
    )
  }
}
