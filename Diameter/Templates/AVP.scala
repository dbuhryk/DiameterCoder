package Diameter.Templates

import java.net.InetAddress
import Diameter.Coder._
import Diameter.Dictionary._
import Diameter.{AvpFlags, _}

/**
  * Created by edzmbuh on 15/04/2016.
  */
object AVP {
  def apply(name:Any, value:Any, vendor:Option[Int]=None, flag:Any = AvpFlags(Seq(AvpFlags.Mandatory)))(implicit dictionary:GenericDictionary):Avp with AvpValue = {
    val avp = (name,vendor) match {
      case (_name:String,Some(_vendor)) => Dictionary.findAvpFirst(_name,_vendor).get
      case (_name:String,None) => Dictionary.findAvpFirst(_name).get
      case (_id:Int,Some(_vendor)) => Dictionary.findAvpFirst(_id,_vendor).get
      case (_id:Int,None) => Dictionary.findAvpFirst(_id).get
      case _ => throw new Exception
    }
    val _vendor = vendor.map(Dictionary.findVendorFirst).collect({case Some(x) => x})
    val newVendor = (_vendor,avp.vendorid) match {
      case (Some(v),_) => Some(v)
      case (None,Some(v)) => Some(v)
      case _ => None
    }
    val flags:AvpFlags = flag match {
      case _flag: String => {
        new AvpFlags((if (_flag.toLowerCase.contains("m")) Seq(AvpFlags.Mandatory) else Nil) ++
          (if (_flag.toLowerCase.contains("p")) Seq(AvpFlags.Protected) else Nil) ++
          (if (_flag.toLowerCase.contains("v")) Seq(AvpFlags.VendorSpecific) else Nil))
      }
      case _flag: Int => new AvpFlags(_flag.toByte)
      case _flag: Byte => new AvpFlags(_flag)
      case _flag: AvpFlags => _flag
    }

    (avp, avp.typeName,value) match {
      case (_, DictionaryAvpTypeValue.UTF8String, _value:String) => AvpUTFString(flags,avp,newVendor,new UTFString(_value))
      case (_, DictionaryAvpTypeValue.DiameterIdentity, _value:String) => AvpDiameterIdentity(flags,avp,newVendor,new UTFString(_value))
      case (_, DictionaryAvpTypeValue.Integer32, _value:Int) => AvpInteger32(flags,avp,newVendor,new Integer32(_value))
      case (_avp:DictionaryAvp with DictionaryAvpEnum, DictionaryAvpTypeValue.Enumerated, _value:Int) => AvpEnumerated(flags,_avp,newVendor,new Integer32(_value))
      case (_avp:DictionaryAvp with DictionaryAvpEnum, DictionaryAvpTypeValue.Enumerated, _value:String) => AvpEnumerated(flags,_avp,newVendor,new Integer32(_avp.enum.collectFirst({case (x,str) if str == _value => x}).getOrElse(0L).toInt))
      case (_, DictionaryAvpTypeValue.IPAddress, _value:String) => AvpIPAddress(flags,avp,newVendor,new IPAddress(InetAddress.getByName(_value)))
      case (_, DictionaryAvpTypeValue.IPAddress, _value:InetAddress) => AvpIPAddress(flags,avp,newVendor,new IPAddress(_value))
      case (_, DictionaryAvpTypeValue.VendorId, _value:Int) => AvpVendorId(flags,avp,newVendor,new Integer32(_value))
      case (_, DictionaryAvpTypeValue.VendorId, _value:String) => AvpVendorId(flags,avp,newVendor,new Integer32(Dictionary.findVendorFirst(_value).map(_.code).get.toInt))
      case (_avp:DictionaryAvp with DictionaryAvpGroup, DictionaryAvpTypeValue.Grouped, _value:Seq[_]) =>AvpGrouped(flags,_avp,newVendor,new Group(_value.asInstanceOf[Seq[Avp with AvpValue]].toList))
    }
  }
  def Result_Code(value:Int)(implicit dictionary:GenericDictionary) = {
    AvpEnumerated(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Result_Code().get, None, new Integer32(value))
  }
  def Result_Code(value:String)(implicit dictionary:GenericDictionary) = {
    val avp = Dictionary.AVP_Result_Code.get
    AvpEnumerated(AvpFlags(Seq(AvpFlags.Mandatory)), avp, None, new Integer32(avp.enum.collectFirst({case (x,_str) if _str == value => x}).get.toInt))
  }
  def Destination_Host(value:String)(implicit dictionary:GenericDictionary) = {
    AvpDiameterIdentity(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Destination_Host.get,None,new UTFString(value))
  }
  def Destination_Realm(value:String)(implicit dictionary:GenericDictionary) = {
    AvpDiameterIdentity(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Destination_Realm.get,None,new UTFString(value))
  }
  def Origin_Host(value:String)(implicit dictionary:GenericDictionary) = {
    AvpDiameterIdentity(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Origin_Host.get,None,new UTFString(value))
  }
  def Origin_Realm(value:String)(implicit dictionary:GenericDictionary) = {
    AvpDiameterIdentity(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Origin_Realm.get,None,new UTFString(value))
  }
  def Session_Id(value:String)(implicit dictionary:GenericDictionary) = {
    AvpUTFString(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Session_Id().get,None,new UTFString(value))
  }
  def Vendor_Id(value:Int)(implicit dictionary:GenericDictionary) = {
    val vendor = dictionary.collect(
      { case x@DictionaryVendor(v, _, _) if v == value => x }:PartialFunction[DictionaryObject,DictionaryVendor]
    ).collectFirst({case x:DictionaryVendor => x})
    AvpVendorId(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Session_Id().get,vendor,new Integer32(value))
  }
  def Vendor_Id(value:String)(implicit dictionary:GenericDictionary) = {
    val vendor = dictionary.collect(
      { case x@DictionaryVendor(_, id, name ) if id == value || name.contains(value) => x }:PartialFunction[DictionaryObject,DictionaryVendor]
    ).collectFirst({case x:DictionaryVendor => x})
    AvpVendorId(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Session_Id().get,vendor,new Integer32(vendor.get.code.toInt))
  }
  def Host_IP_Address(value:String)(implicit dictionary:GenericDictionary) = {
    AvpIPAddress(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Session_Id().get,None,new IPAddress(InetAddress.getByName(value)))
  }
  def Host_IP_Address(value:InetAddress)(implicit dictionary:GenericDictionary) = {
    AvpIPAddress(AvpFlags(Seq(AvpFlags.Mandatory)), Dictionary.AVP_Session_Id().get,None,new IPAddress(value))
  }

}
