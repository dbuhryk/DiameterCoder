package diameter

import java.net.InetAddress
import diameter.Dictionary._
import diameter.ValueContainers._
import sun.reflect.generics.reflectiveObjects.NotImplementedException

object AVP {
  def apply(name:Any, value:Any, vendor:Option[Any]=None, flag:Any = AvpFlags(Seq()))(implicit dictionary:GenericDictionary):Avp = {
    val _vendor = vendor.map({
      case x:String => DictionaryVendor.apply(x)
      case x:Int => DictionaryVendor.apply(x)
    }).collect({case Some(x) => x})
    val avp = (name,vendor) match {
      case (_name:String,Some(_)) => DictionaryAvp(_name,_vendor.get.code.toInt)
      case (_name:String,None) => DictionaryAvp(_name)
      case (_id:Int,Some(_)) => DictionaryAvp(_id,_vendor.get.code.toInt)
      case (_id:Int,None) => DictionaryAvp(_id)
      case _ => throw new Exception
    }
    val newVendor = (_vendor,avp.vendorid) match {
      case (Some(v),_) => Some(v)
      case (None,Some(v)) => Some(v)
      case _ => None
    }



    val flags:AvpFlags = AvpFlags( (
      ( (flag match {
        case _flag: String => {
          new AvpFlags((if (_flag.toLowerCase.contains("m")) Seq(AvpFlags.Mandatory) else Nil) ++
            (if (_flag.toLowerCase.contains("p")) Seq(AvpFlags.Protected) else Nil) ++
            (if (_flag.toLowerCase.contains("v")) Seq(AvpFlags.VendorSpecific) else Nil))
        }
        case _flag: Int => new AvpFlags(_flag.toByte)
        case _flag: Byte => new AvpFlags(_flag)
        case _flag: AvpFlags => _flag
      }).value |
        //({if (newVendor.isDefined) AvpFlags(Seq(AvpFlags.VendorSpecific)) else AvpFlags(0.toByte)}).value |
        ({if (avp.flags.flagMadatory == DictionaryAvpFlagValue.must) AvpFlags(Seq(AvpFlags.Mandatory)) else AvpFlags(0.toByte)}).value |
        ({if (avp.flags.flagVendorbit == DictionaryAvpFlagValue.must) AvpFlags(Seq(AvpFlags.VendorSpecific)) else AvpFlags(0.toByte)}).value
      ) &
        ~(({if (avp.flags.flagMadatory == DictionaryAvpFlagValue.mustnot) AvpFlags(Seq(AvpFlags.Mandatory)) else AvpFlags(0.toByte)}).value) &
        ~(({if (avp.flags.flagVendorbit == DictionaryAvpFlagValue.mustnot) AvpFlags(Seq(AvpFlags.VendorSpecific)) else AvpFlags(0.toByte)}).value)
      ).toByte )

    (avp, avp.typeName,value) match {
      case (_, DictionaryAvpTypeValue.UTF8String, _value:String) => AvpUTFString(flags,avp,newVendor,new UTFString(_value))
      case (_, DictionaryAvpTypeValue.DiameterIdentity, _value:String) => AvpUTFString(flags,avp,newVendor,new UTFString(_value))
      case (_, DictionaryAvpTypeValue.DiameterURI, _value:String) => AvpUTFString(flags,avp,newVendor,new UTFString(_value))
      case (_, DictionaryAvpTypeValue.Integer32, _value:Int) => AvpInteger32(flags,avp,newVendor,new Integer32(_value))
      case (_, DictionaryAvpTypeValue.Integer64, _value:Int) => AvpInteger64(flags,avp,newVendor,new Integer64(_value.toLong))
      case (_, DictionaryAvpTypeValue.Integer64, _value:Long) => AvpInteger64(flags,avp,newVendor,new Integer64(_value))
      case (_, DictionaryAvpTypeValue.Unsigned32, _value:Int) => AvpUnsigned32(flags,avp,newVendor,new Integer32(_value))
      case (_, DictionaryAvpTypeValue.Unsigned64, _value:Int) => AvpUnsigned64(flags,avp,newVendor,new Integer64(_value.toLong))
      case (_, DictionaryAvpTypeValue.Unsigned64, _value:Long) => AvpUnsigned64(flags,avp,newVendor,new Integer64(_value))
      case (_avp:DictionaryAvp with DictionaryAvpEnum, DictionaryAvpTypeValue.Unsigned32, _value:String) => AvpUnsigned32(flags,avp,newVendor,new Integer32(_avp.enum.collectFirst({case (x,str) if str == _value => x}).getOrElse(0L).toInt))
      case (_avp:DictionaryAvp with DictionaryAvpEnum, DictionaryAvpTypeValue.Integer32, _value:String) => AvpUnsigned32(flags,avp,newVendor,new Integer32(_avp.enum.collectFirst({case (x,str) if str == _value => x}).getOrElse(0L).toInt))
      case (_avp:DictionaryAvp, DictionaryAvpTypeValue.AppId, _value:Int) => AvpUnsigned32(flags,_avp,newVendor,new Integer32(_value))
      case (_avp:DictionaryAvp with DictionaryAvpEnum, DictionaryAvpTypeValue.AppId, _value:String) => AvpUnsigned32(flags,_avp,newVendor,new Integer32(_avp.enum.collectFirst({case (x,str) if str == _value => x}).getOrElse(0L).toInt))

      case (_avp:DictionaryAvp with DictionaryAvpEnum, DictionaryAvpTypeValue.Enumerated, _value:Int) => AvpEnumerated(flags,_avp,newVendor,new Integer32(_value))
      case (_avp:DictionaryAvp with DictionaryAvpEnum, DictionaryAvpTypeValue.Enumerated, _value:String) => AvpEnumerated(flags,_avp,newVendor,new Integer32(_avp.enum.collectFirst({case (x,str) if str == _value => x}).getOrElse(0L).toInt))
      case (_, DictionaryAvpTypeValue.IPAddress, _value:String) => AvpIPAddress(flags,avp,newVendor,new IPAddress(InetAddress.getByName(_value)))
      case (_, DictionaryAvpTypeValue.IPAddress, _value:InetAddress) => AvpIPAddress(flags,avp,newVendor,new IPAddress(_value))
      case (_, DictionaryAvpTypeValue.VendorId, _value:Int) => AvpVendorId(flags,avp,newVendor,new Integer32(_value))
      case (_, DictionaryAvpTypeValue.VendorId, _value:String) => AvpVendorId(flags,avp,newVendor,new Integer32(DictionaryVendor(_value).map(_.code).get.toInt))
      case (_, DictionaryAvpTypeValue.OctetString, _value:String) => AvpOctetString(flags,avp,newVendor,new OctetString(_value))
      case (_avp:DictionaryAvp with DictionaryAvpGroup, DictionaryAvpTypeValue.Grouped, _value:Seq[_]) =>AvpGrouped(flags,_avp,newVendor,new Group(_value.asInstanceOf[Seq[Avp]].toList))
      case (_, DictionaryAvpTypeValue.Time, _value:String) => AvpInteger32(flags,avp,newVendor,new Integer32(Converter.string2Time(_value)))
      case (_, DictionaryAvpTypeValue.Time, _value:Int) => AvpInteger32(flags,avp,newVendor,new Integer32(_value))
      case (_) => throw new NotImplementedException
    }
  }

}
