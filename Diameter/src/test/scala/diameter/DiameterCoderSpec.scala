package diameter

import java.net.InetAddress

import diameter.Coder.DiameterCoder
import diameter.Dictionary.DictionaryAvpTypeValue
import diameter.Dictionary.xml.DictionaryXMLStorage
import diameter.ValueContainers._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class DiameterCoderSpec  extends FlatSpec with BeforeAndAfterAll{
  implicit val dictionary:DictionaryXMLStorage = new DictionaryXMLStorage(getClass.getResource("/dictionary/dictionary.xml").getPath)

  behavior of "Basic type coder"

  it should "encode Integer32" in {
    val v = new Integer32(0x00abcdef)
    assert (v.value == 0x00abcdef)
    assert (v.dataRaw == List(0x00,0xab,0xcd,0xef).map(_.toByte) )
  }

  it should "decode Integer32" in {
    val v = new Integer32(List(0x00,0xab,0xcd,0xef).map(_.toByte))
    assert (v.value == 0x00abcdef)
  }

  it should "encode max Integer32" in {
    val v = new Integer32(0xffffffff)
    assert (v.value == 0xffffffff)
    assert (v.dataRaw == List(0xff,0xff,0xff,0xff).map(_.toByte) )
  }

  it should "decode max Integer32" in {
    val v = new Integer32(List(0xff,0xff,0xff,0xff).map(_.toByte))
    assert (v.value == 0xffffffff)
  }

  it should "encode simple UTFString" in {
    val v = new UTFString("abcdefghijklmnopqrstuvwxyz")
    assert (v.value == "abcdefghijklmnopqrstuvwxyz")
    assert (v.dataRaw == List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/,0x65/*e*/,0x66/*f*/,0x67/*g*/,
      0x68/*h*/,0x69/*i*/,0x6A/*j*/,0x6B/*k*/,0x6C/*l*/,0x6D/*m*/,0x6E/*n*/,
      0x6F/*o*/,0x70/*p*/,0x71/*q*/,0x72/*r*/,0x73/*s*/,0x74/*t*/,0x75/*u*/,
      0x76/*v*/,0x77/*w*/,0x78/*x*/,0x79/*w*/,0x7A/*z*/
    ).map(_.toByte))
  }

  it should "decode simple UTFString" in {
    val v = new UTFString(List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/,0x65/*e*/,0x66/*f*/,0x67/*g*/,
      0x68/*h*/,0x69/*i*/,0x6A/*j*/,0x6B/*k*/,0x6C/*l*/,0x6D/*m*/,0x6E/*n*/,
      0x6F/*o*/,0x70/*p*/,0x71/*q*/,0x72/*r*/,0x73/*s*/,0x74/*t*/,0x75/*u*/,
      0x76/*v*/,0x77/*w*/,0x78/*x*/,0x79/*w*/,0x7A/*z*/
    ).map(_.toByte))
    assert (v.value == "abcdefghijklmnopqrstuvwxyz")
  }

  it should "encode HEX OctetString" in {
    val v = new OctetString("0x6162636465666768696a6b6c6d6e6f707172737475767778797a")
    assert (v.dataRaw == List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/,0x65/*e*/,0x66/*f*/,0x67/*g*/,
      0x68/*h*/,0x69/*i*/,0x6A/*j*/,0x6B/*k*/,0x6C/*l*/,0x6D/*m*/,0x6E/*n*/,
      0x6F/*o*/,0x70/*p*/,0x71/*q*/,0x72/*r*/,0x73/*s*/,0x74/*t*/,0x75/*u*/,
      0x76/*v*/,0x77/*w*/,0x78/*x*/,0x79/*w*/,0x7A/*z*/
    ).map(_.toByte))
  }

  it should "encode HEX OctetString with spaces" in {
    val v = new OctetString("0x61 62,6364")
    assert (v.dataRaw == List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/
    ).map(_.toByte))
  }

  it should "encode ASCII OctetString with spaces" in {
    val v = new OctetString("abcd")
    assert (v.dataRaw == List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/
    ).map(_.toByte))
  }

  it should "decode simple OctetString" in {
    val v = new OctetString(List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/,0x65/*e*/,0x66/*f*/,0x67/*g*/,
      0x68/*h*/,0x69/*i*/,0x6A/*j*/,0x6B/*k*/,0x6C/*l*/,0x6D/*m*/,0x6E/*n*/,
      0x6F/*o*/,0x70/*p*/,0x71/*q*/,0x72/*r*/,0x73/*s*/,0x74/*t*/,0x75/*u*/,
      0x76/*v*/,0x77/*w*/,0x78/*x*/,0x79/*w*/,0x7A/*z*/
    ).map(_.toByte))
    assert (v.value == "6162636465666768696a6b6c6d6e6f707172737475767778797a")
  }

  it should "encode IPv4 as IPAddress" in {
    val address = InetAddress.getByName("1.2.3.4")
    val v = new IPAddress(address)
    assert (v.value == address)
    assert (v.dataRaw == List(0x00,0x01,0x01,0x02,0x03,0x04).map(_.toByte))
  }

  it should "decode IPv4 as IPAddress" in {
    val address = InetAddress.getByName("1.2.3.4")
    val v = new IPAddress(List(0x00,0x01,0x01,0x02,0x03,0x04).map(_.toByte))
    assert (v.value == address)
  }

  it should "encode IPv6 as IPAddress" in {
    val address = InetAddress.getByName("102:304:506:708:90A:B0C:D0E:F00")
    val v = new IPAddress(address)
    assert (v.value == address)
    assert (v.dataRaw == List(0x00,0x02,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x00).map(_.toByte))
  }

  it should "decode IPv6 as IPAddress" in {
    val address = InetAddress.getByName("102:304:506:708:90A:B0C:D0E:F00")
    val v = new IPAddress(List(0x00,0x02,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x00).map(_.toByte))
    assert (v.value == address)
  }

  behavior of "Diameter AVP Factory"

  it should "instantiate AVP from code as String" in {
    val avp = AVP("NAS-Port",10).asInstanceOf[AvpUnsigned32]
    assert (avp.avp.code == 5L)
  }

  it should "instantiate Unsigned32 from Int" in {
    val avp = AVP(5,10).asInstanceOf[AvpUnsigned32]
    assert (avp.avp.code == 5L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.Unsigned32)
    assert (avp.value.value == 10)
    assert (avp.value.dataRaw == List(0,0,0,10))
  }

  it should "instantiate Integer32 from Int" in {
    val avp = AVP(47,10).asInstanceOf[AvpInteger32]
    assert (avp.avp.code == 47L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.Integer32)
    assert (avp.value.value == 10)
    assert (avp.value.dataRaw == List(0,0,0,10))
  }

  it should "instantiate Enumerated from Int" in {
    val avp = AVP(273,0).asInstanceOf[AvpEnumerated]
    assert (avp.avp.code == 273L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.Enumerated)
    assert (avp.value.value == 0)
    assert (avp.value.dataRaw == List(0,0,0,0))
  }

  it should "instantiate Enumerated from String" in {
    val avp = AVP(273,"REBOOTING").asInstanceOf[AvpEnumerated]
    assert (avp.avp.code == 273L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.Enumerated)
    assert (avp.value.value == 0)
    assert (avp.avp.enum(0) == "REBOOTING")
    assert (avp.value.dataRaw == List(0,0,0,0))
  }

  it should "instantiate AppId as AvpEnumerated from Int" in {
    val avp = AVP(258,10).asInstanceOf[AvpUnsigned32]
    assert (avp.avp.code == 258L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.AppId)
    assert (avp.value.value == 10)
    assert (avp.value.dataRaw == List(0,0,0,10))
  }

  it should "instantiate UTFString from String" in {
    val avp = AVP(1,"abcdefghijklmnopqrstuvwxyz").asInstanceOf[AvpUTFString]
    assert (avp.avp.code == 1L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.UTF8String)
    assert (avp.value.value == "abcdefghijklmnopqrstuvwxyz")
    assert (avp.value.dataRaw == List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/,0x65/*e*/,0x66/*f*/,0x67/*g*/,
      0x68/*h*/,0x69/*i*/,0x6A/*j*/,0x6B/*k*/,0x6C/*l*/,0x6D/*m*/,0x6E/*n*/,
      0x6F/*o*/,0x70/*p*/,0x71/*q*/,0x72/*r*/,0x73/*s*/,0x74/*t*/,0x75/*u*/,
      0x76/*v*/,0x77/*w*/,0x78/*x*/,0x79/*w*/,0x7A/*z*/).map(_.toByte))
  }

  it should "instantiate DiameterIdentity from String" in {
    val avp = AVP(264,"abcd.efgh").asInstanceOf[AvpUTFString]
    assert (avp.avp.code == 264L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.DiameterIdentity)
    assert (avp.value.value == "abcd.efgh")
    assert (avp.value.dataRaw == List(
      0x61/*a*/,0x62/*b*/,0x63/*c*/,0x64/*d*/,0x2E/*.*/,0x65/*e*/,0x66/*f*/,0x67/*g*/,0x68/*h*/).map(_.toByte))
  }

  it should "instantiate DiameterURI from String" in {
    val avp = AVP(292,"aaa://abcd.efgh:12345").asInstanceOf[AvpUTFString]
    assert (avp.avp.code == 292L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.DiameterURI)
    assert (avp.value.value == "aaa://abcd.efgh:12345")
    assert (avp.value.dataRaw == List(
      0x61/*a*/,0x61/*a*/,0x61/*a*/,0x3A/*:*/,0x2F/*/ */,0x2F/*/ */,0x61/*a*/,
      0x62/*b*/,0x63/*c*/,0x64/*d*/,0x2E/*.*/,0x65/*e*/,0x66/*f*/,0x67/*g*/,
      0x68/*h*/,0x3A/*:*/,0x31/*1*/,0x32/*2*/,0x33/*3*/,0x34/*4*/,0x35/*5*/).map(_.toByte))
  }

  it should "instantiate IPAddress from String" in {
    val avp = AVP(8,"1.2.3.4").asInstanceOf[AvpIPAddress]
    assert (avp.avp.code == 8L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.IPAddress)
    assert (avp.value.value == InetAddress.getByName("1.2.3.4"))
    assert (avp.value.dataRaw == List(0x00,0x01,0x01,0x02,0x03,0x04).map(_.toByte))
  }

  it should "instantiate VendorId from Int" in {
    val avp = AVP(265,1).asInstanceOf[AvpVendorId]
    assert (avp.avp.code == 265L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.VendorId)
    assert (avp.value.value == 1)
    assert (avp.value.dataRaw == List(0x00,0x00,0x00,0x01).map(_.toByte))
  }

  it should "instantiate VendorId from String" in {
    val avp = AVP(265,"3GPP").asInstanceOf[AvpVendorId]
    assert (avp.avp.code == 265L)
    assert (avp.avp.typeName == DictionaryAvpTypeValue.VendorId)
    assert (avp.value.value == 10415)
    assert (avp.value.dataRaw == List(0x00,0x00,0x28,0xaf).map(_.toByte))
  }

  it should "NOT instantiate vendor specific AVP without Vendor" in {
    intercept[Exception] {AVP(824,"BYE").asInstanceOf[AvpUTFString]}
  }

  it should "instantiate AVP with mandatory=mustnot Flags" in {
    val avp = AVP(1444,"user",Some(10415),"M").asInstanceOf[AvpUTFString]
    assert (avp.avp.code == 1444L)
    assert (!avp.flags.isMandatory)
  }

  it should "instantiate AVP with vendor=mustnot Flags" in {
    val avp = AVP(2,"",None,"V").asInstanceOf[AvpOctetString]
    assert (avp.avp.code == 2L)
    assert (!avp.flags.isVendorSpecific)
  }

  it should "instantiate AVP with mandatory=must Flags" in {
    val avp = AVP(2,"",None,"").asInstanceOf[AvpOctetString]
    assert (avp.avp.code == 2L)
    assert (avp.flags.isMandatory)
  }

  it should "instantiate AVP with Vendor" in {
    val avp = AVP(824,"BYE",Some(10415)).asInstanceOf[AvpUTFString]
    assert (avp.avp.code == 824L)
    assert (avp.vendor.isDefined)
    assert (avp.flags.isVendorSpecific)
  }

  it should "instantiate Time from String" in {
    val avp = AVP(834,"[yyyy-MM-dd]1970-01-01",Some(10415)).asInstanceOf[AvpInteger32]
    assert (avp.avp.code == 834L)
    assert (avp.value.dataRaw == List(0x83,0xaa,0x7e,0x80).map(_.toByte))
  }

  behavior of "Diameter AVP Coder"

  it should "encode AVP code" in {
    val avp = AVP(824,"BYE",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert ( eavp.codeRaw == List(0x00,0x00,0x03,0x38).map(_.toByte) )
  }

  it should "encode AVP flags [MV]" in {
    val avp = AVP(824,"BYE",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert ( eavp.flagsRaw == List(0xc0).map(_.toByte) )
  }

  it should "encode AVP vendor" in {
    val avp = AVP(824,"BYE",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert ( eavp.vendorIdRaw.get == List(0x00,0x00,0x28,0xaf).map(_.toByte) )
  }

  it should "encode AVP length" in {
    val avp = AVP(824,"BYE",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert ( eavp.length == 15 )
    assert ( eavp.lengthRaw == List(0x00,0x00,0x0f).map(_.toByte) )
  }

  it should "encode AVP data" in {
    val avp = AVP(824,"BYE",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert(eavp.dataRaw == new UTFString("BYE").dataRaw )
    assert(eavp.bodyRaw.last == 0x00)
  }

  it should "encode AVP padding []" in {
    val avp = AVP(824,"BYEE",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert(eavp.bodyRaw.length == 16)
    assert(eavp.bodyRaw(15) != 0x00)
  }

  it should "encode AVP padding 0x00" in {
    val avp = AVP(824,"BYE",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert(eavp.bodyRaw.length == 16)
    assert(eavp.bodyRaw(15) == 0x00)
  }

  it should "encode AVP padding 0x0000" in {
    val avp = AVP(824,"BY",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert(eavp.bodyRaw.length == 16)
    assert(eavp.bodyRaw(14) == 0x00)
    assert(eavp.bodyRaw(15) == 0x00)
  }

  it should "encode AVP padding 0x000000" in {
    val avp = AVP(824,"B",Some(10415)).asInstanceOf[AvpUTFString]
    val eavp = DiameterCoder.encodeAvp(avp)
    assert(eavp.bodyRaw.length == 16)
    assert(eavp.bodyRaw(13) == 0x00)
    assert(eavp.bodyRaw(14) == 0x00)
    assert(eavp.bodyRaw(15) == 0x00)
  }

  it should "encode group AVP" in {
    val avp1 = AVP(834,"[yyyy-MM-dd]1970-01-01",Some(10415))
    val eavp1 = DiameterCoder.encodeAvp(avp1)
    val avp2 = AVP(835,"[yyyy-MM-dd]1970-01-01",Some(10415))
    val eavp2 = DiameterCoder.encodeAvp(avp2)
    val avp = AVP(833,Seq(avp1,avp2),Some(10415)).asInstanceOf[AvpGrouped]
    assert (avp.avp.typeName == DictionaryAvpTypeValue.Grouped)
    val eavp = DiameterCoder.encodeAvp(avp)
    assert(eavp.bodyRaw.length == 44)
    assert(eavp.bodyRaw.drop(12).startsWith(eavp1.bodyRaw))
    assert(eavp.bodyRaw.drop(28).startsWith(eavp2.bodyRaw))
  }
}
