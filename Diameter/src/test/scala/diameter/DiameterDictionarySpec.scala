package diameter

import diameter.Dictionary._
import diameter.Dictionary.xml.DictionaryXMLStorage
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

/**
  * Created by edzmbuh on 16/12/2016.
  */
class DiameterDictionarySpec extends FlatSpec with BeforeAndAfterAll{
  implicit val dictionary:DictionaryXMLStorage = new DictionaryXMLStorage(getClass.getResource("/dictionary/dictionary.xml").getPath)

  behavior of "Dictionary Vendor function"

  it should "locate Vendor-Id" in {
    val vendor = DictionaryVendor.apply("TGPP")
    assert (vendor.get.code == 10415L)
  }

  it should "locate Vendor name" in {
    val vendor = DictionaryVendor.apply("3GPP")
    assert (vendor.get.code == 10415L)
  }

  it should "locate Vendor code" in {
    val vendor = DictionaryVendor.apply(10415)
    assert (vendor.get.code == 10415L)
  }

  behavior of "Dictionary AVP function"

  it should "locate AVP name" in {
    val avp = DictionaryAvp("Result-Code")
    assert (avp.code == 268L)
  }

  it should "locate AVP code" in {
    val avp = DictionaryAvp(268)
    assert (avp.name.contains("Result-Code"))
  }

  it should "locate AVP name with Vendor code" in {
    val avp = DictionaryAvp("Event",10415)
    assert (avp.code == 825L)
    assert (avp.vendorid.get.code == 10415L)
  }

  it should "locate AVP in application branch" in {
    val avp = DictionaryAvp(701,10415)
    assert (avp.name.contains("MSISDN"))
    assert (avp.vendorid.get.code == 10415L)
  }

  it should "locate AVP Enumeration" in {
    val avp = DictionaryAvp("Result-Code").asInstanceOf[DictionaryAvp with DictionaryAvpEnum]
    assert (avp.typeName == DictionaryAvpTypeValue.Enumerated)
    assert (avp.enum(2001L) == "DIAMETER_SUCCESS")
  }

  it should "locate AVP Grouped" in {
    val avp = DictionaryAvp("Experimental-Result").asInstanceOf[DictionaryAvp with DictionaryAvpGroup]
    assert (avp.typeName == DictionaryAvpTypeValue.Grouped)
    assert (avp.group.head.code == 266L)
  }

  behavior of "Dictionary Command function"

  it should "load Command by code" in {
    val command = DictionaryCommand(257)
    assert (command.name == "Capabilities-Exchange")
  }

  it should "load Command by name" in {
    val command = DictionaryCommand("Capabilities-Exchange")
    assert (command.code == 257L)
  }

  it should "load Command by code in application branch" in {
    val command = DictionaryCommand("User-Data")
    assert (command.code == 306L)
  }

  behavior of "Dictionary Application function"

  it should "load Application by id" in {
    val application = DictionaryApplication(3)
    assert (application.name == "Diameter Base Accounting")
  }

  it should "load Application by name" in {
    val application = DictionaryApplication("Diameter Base Accounting")
    assert (application.id == 3L)
  }

  it should "load Application from external branch" in {
    val application = DictionaryApplication(16777217)
    assert (application.name == "3GPP Sh")
  }
}
