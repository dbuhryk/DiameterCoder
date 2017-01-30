package diameter.Dictionary.xml

import java.io.BufferedReader
import java.net.URL
import diameter.Dictionary._
import scala.xml.Elem

/**
  * Created by edzmbuh on 22/03/2016.
  * Wireshark dictionary reader
  */
trait XMLDictionary {
  val xmlRoot:Elem
  lazy val avps:Seq[DictionaryAvp] = locateAvps(xmlRoot)
  lazy val applications:Seq[DictionaryApplication] = locateApplications(xmlRoot)
  lazy val vendors:Seq[DictionaryVendor] = locateVendors(xmlRoot)
  lazy val commands:Seq[DictionaryCommand] = locateCommands(xmlRoot)
  def locateAvps(elem:Elem) = {
    val avpSeq = xmlRoot \\ "avp"

    avpSeq.map((node) => {
      val avpCode = (node \ "@code").text.toLong

      val typeName = {
          ( node \ "type" \"@type-name" ).text match {
            case "" if ((node \ "grouped").nonEmpty) => "Grouped"
            case x => x
          }
        }

      val isEnumLike = ( node \ "enum" ).nonEmpty

      //name IDREF #REQUIRED
      //val avpName = (node \ "@name").text
      val avpName = (node \ "@name").text match {case "" | "None" => None; case x => Some(x)}

      //vendor-id IDREF #IMPLIED
      //val avpVendorid = vendors.find(_.name==(node \ "@vendor-id").text)
      val avpVendorid = vendors.find(_.vendorId == (node \ "@vendor-id").text)

      //may-encrypt (yes | no) "yes"
      val avpFlagMayencrypt = (node \ "@may-encrypt").text match {
        case "yes" => true
        case "no" => false
        case _ => true
      }
      //mandatory (must | may | mustnot | shouldnot) "may"
      val avpFlagMandatory = (node \ "@mandatory").text match {
        case "must" => DictionaryAvpFlagValue.must
        case "may" => DictionaryAvpFlagValue.may
        case "mustnot" => DictionaryAvpFlagValue.mustnot
        case "shouldnot" => DictionaryAvpFlagValue.shouldnot
        case _ => DictionaryAvpFlagValue.may
      }

      //protected (must | may | mustnot | shouldnot) "may"
      val avpFlagProtected = (node \ "@protected").text match {
        case "must" => DictionaryAvpFlagValue.must
        case "may" => DictionaryAvpFlagValue.may
        case "mustnot" => DictionaryAvpFlagValue.mustnot
        case "shouldnot" => DictionaryAvpFlagValue.shouldnot
        case _ => DictionaryAvpFlagValue.may
      }

      //vendor-bit (must | may | mustnot | shouldnot) "mustnot"
      val avpFlagVendorbit = (node \ "@vendor-bit").text match {
        case "must" => DictionaryAvpFlagValue.must
        case "may" => DictionaryAvpFlagValue.may
        case "mustnot" => DictionaryAvpFlagValue.mustnot
        case "shouldnot" => DictionaryAvpFlagValue.shouldnot
        case _ => DictionaryAvpFlagValue.mustnot
      }
      //constrained (true | false) "false"
      //description CDATA #IMPLIED
      //code CDATA #REQUIRED
      //val code1=code

      //println (avpName +"("+ avpCode+")"+":"+typeName)
      val avpType = typeName match {
        case "UTF8String" => DictionaryAvpTypeValue.UTF8String
        case "IPAddress" => DictionaryAvpTypeValue.IPAddress
        case "DiameterIdentity" => DictionaryAvpTypeValue.DiameterIdentity
        case "IPFilterRule" => DictionaryAvpTypeValue.IPFilterRule
        case "QoSFilterRule" => DictionaryAvpTypeValue.QoSFilterRule
        case "MIPRegistrationRequest" => DictionaryAvpTypeValue.MIPRegistrationRequest
        case "VendorId" => DictionaryAvpTypeValue.VendorId
        case "AppId" => DictionaryAvpTypeValue.AppId
        case "Enumerated" => DictionaryAvpTypeValue.Enumerated
        case "OctetString" => DictionaryAvpTypeValue.OctetString
        case "DiameterURI" => DictionaryAvpTypeValue.DiameterURI
        case "Integer32" => DictionaryAvpTypeValue.Integer32
        case "Unsigned32" => DictionaryAvpTypeValue.Unsigned32
        case "Integer64" => DictionaryAvpTypeValue.Integer64
        case "Unsigned64" => DictionaryAvpTypeValue.Unsigned64
        case "Time" => DictionaryAvpTypeValue.Time
        case "Grouped" => DictionaryAvpTypeValue.Grouped
        case "Float32" => DictionaryAvpTypeValue.Float32
        case "Float64" => DictionaryAvpTypeValue.Float32
        case _ => throw new Exception("Unknown type:" + typeName + " at AVP " + avpName +", "+ avpCode)
      }

      (avpType, isEnumLike) match {
        case ( DictionaryAvpTypeValue.Grouped, _ ) =>
          new DictionaryAvp(
            code = avpCode,
            name = avpName,
            typeName = avpType,
            vendorid = avpVendorid,
            flags = DictionaryAvpFlags(avpFlagMayencrypt,avpFlagMandatory,avpFlagProtected,avpFlagVendorbit)
          ) with DictionaryAvpGroup {
            lazy val group =(node \\ "gavp").foldLeft(List.empty[String])(
              (a, b) => a ++ b.attribute("name").map( _.text )).map( x => avps.find(_.name.contains(x) ) ).collect( {case Some(x) => x}
            )
          }
        case ( DictionaryAvpTypeValue.Enumerated, _ ) | ( _, true )=>
          new DictionaryAvp(
            code = avpCode,
            name = avpName,
            typeName = avpType,
            vendorid = avpVendorid,
            flags = DictionaryAvpFlags(avpFlagMayencrypt,avpFlagMandatory,avpFlagProtected,avpFlagVendorbit)
          ) with DictionaryAvpEnum {
            val enum = (node \\ "enum").foldLeft(Map[Long, String]())(
              (a, b) => if (b.attribute("code").isEmpty) a else a + (b.attribute("code").head.text.toLong -> b.attribute("name").head.text)
            )
          }
        case _ => new DictionaryAvp(
          code = avpCode,
          name = avpName,
          typeName = avpType,
          vendorid = avpVendorid,
          flags = DictionaryAvpFlags(avpFlagMayencrypt,avpFlagMandatory,avpFlagProtected,avpFlagVendorbit)
        )
      }
    })
  }
  def locateApplications(elem:Elem) = {
    (elem \\ "application").map( (node) => {
      new DictionaryApplication(
        id =(node \ "@id").text.toLong,
        name = (node \ "@name").text
        //uri = (node \ "@uri").text match {case "" | "None" => None; case x => Some(x)},
        //commandList = Seq.empty[DictionaryCommand],
        //avpList = Seq.empty[DictionaryAvp]
      )
    })
  }
  def locateVendors(elem:Elem) = {
    (elem \\ "vendor").map( (node) => {
      new DictionaryVendor(
        code = (node \ "@code").text.toLong,
        vendorId = (node \ "@vendor-id").text,
        name = (node \ "@name").text match {case "" | "None" => None; case x => Some(x)}
      )
    })
  }
  def locateCommands(elem:Elem) = {
    (elem \\ "command").
      filter(_.attribute("code").isDefined).
      filter(_.attribute("vendor-id").isDefined).
      map( (node) =>
        new DictionaryCommand(
          code = (node \ "@code").text.toLong,
          name = (node \ "@name").text
          //vendor = (node \ "@vendor-id").text match {case "" | "None" => None; case x => vendors.find(_.ref._2==x)}
        ))
  }
}

class DictionaryXMLStorage(val xmlRoot:Elem) extends GenericDictionary with XMLDictionary{
  //scala.xml.XML.load(reader) = reader:BufferedReader
  def this(str: String) = this(scala.xml.XML.load(str))
  def this(reader:BufferedReader) = this(scala.xml.XML.load(reader))
  def this(url:URL) = this(scala.xml.XML.load(url))
  override def iterator:Iterator[DictionaryObject] = avps.iterator ++ vendors.iterator ++ commands.iterator ++ applications.iterator
}
