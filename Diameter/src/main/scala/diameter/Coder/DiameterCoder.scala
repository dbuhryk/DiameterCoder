package diameter.Coder

import java.net.{Inet4Address, Inet6Address, InetAddress}

import diameter.Dictionary._
import diameter.ValueContainers._
import diameter._

/**
  * Created by edzmbuh on 04/04/2016.
  */
object DiameterCoder {


  def decodeEarlyMessage(bs:Seq[Byte]):EarlyMessage = {
    implicit val byteOrder = java.nio.ByteOrder.BIG_ENDIAN
    new EarlyMessage {
      override val bodyRaw: Seq[Byte] = bs
    }
  }
  def decodeEarlyAvpGroup(bs:Seq[Byte]):Seq[EarlyAvp] ={
    def readNextAvp(avpRaws: Seq[Byte]): Seq[Seq[Byte]] = {
      val avpLen = avpRaws.slice(5, 8).foldLeft(0)((a, b) => (a << 8) + (b & 0xFF))
      val avpLenWithPadding = (avpLen % 4) match {
        case 0 => 0
        case 1 => 3
        case 2 => 2
        case 3 => 1
      }
      val len = avpLen + avpLenWithPadding
      val avpRaw = avpRaws.slice(0, len)
      //TODO: make tail recursion
      Seq(avpRaw) ++ (if (avpRaws.length>len) readNextAvp(avpRaws.drop(len)) else (Seq.empty[Seq[Byte]]))
    }
    if (bs.size == 0) Seq.empty[EarlyAvp] else
    readNextAvp(bs).map( x => new EarlyAvp {
      override val bodyRaw: Seq[Byte] = x
    })
  }
  def decodeEarlyAvp(bs:Seq[Byte]):EarlyAvp = {
    implicit val byteOrder = java.nio.ByteOrder.BIG_ENDIAN
    new EarlyAvp {
      override val bodyRaw: Seq[Byte] = bs
    }
  }
  def decodeMessage(em:EarlyMessage)(implicit dictionary:GenericDictionary):Message = {
    val cmd = dictionary.collect({case c:DictionaryCommand => c}).find(_.code == em.cmdCode).get//OrElse(throw new Exception("Unknown CMD code "+cmdCode))
    val app = dictionary.collect({case a:DictionaryApplication => a}).find(_.id == em.appId).get//OrElse(throw new Exception("Unknown APP code "+appCode))
    val avps = em.avps.map( decodeAvp )
    new Message(em.flags,cmd,app,em.hbh,em.ete,avps)
  }
  def encodeMessage(me:Message):EarlyMessage = {
    val _verRaw:Seq[Byte] = Seq(1.toByte)
    val _flagsRaw:Seq[Byte] = Seq(me.flag.value)
    val _cmdCodeRaw:Seq[Byte] = encodeInt32(me.cmd.code.toInt).drop(1)
    val _appIdRaw:Seq[Byte] = encodeInt32(me.app.id.toInt)
    val _hbhRaw:Seq[Byte] = encodeInt32(me.hbh)
    val _e2eRaw:Seq[Byte] = encodeInt32(me.ete)
    val _avpsRaw = encodeAvpGroup(me.avps)
    val _lenRaw = encodeInt32(_avpsRaw.length + 20).drop(1)

    new EarlyMessage {
      override val bodyRaw: Seq[Byte] = _verRaw ++ _lenRaw ++ _flagsRaw ++ _cmdCodeRaw ++ _appIdRaw ++ _hbhRaw ++
        _e2eRaw ++ _avpsRaw
    }
  }
  def encodeInt32(i:Int):Seq[Byte] = {
    Seq(
      ((i >> 24) & 0xff).toByte,
      ((i >> 16) & 0xff).toByte,
      ((i >> 8) & 0xff).toByte,
      ((i >> 0) & 0xff).toByte)
  }
  def encodeInt64(i:Long):Seq[Byte] = {
    Seq(
      ((i >> 56) & 0xff).toByte,
      ((i >> 48) & 0xff).toByte,
      ((i >> 40) & 0xff).toByte,
      ((i >> 32) & 0xff).toByte,
      ((i >> 24) & 0xff).toByte,
      ((i >> 16) & 0xff).toByte,
      ((i >> 8) & 0xff).toByte,
      ((i >> 0) & 0xff).toByte)
  }
  def encodeAvp(avp:Avp): EarlyAvp = {
    //implicit val byteOrder = java.nio.ByteOrder.BIG_ENDIAN
    //val codeRaw:Seq[Byte] = (new ByteStringBuilder).putInt(avp.avp.code.toInt)(java.nio.ByteOrder.BIG_ENDIAN).result()
    val _codeRaw:Seq[Byte] = encodeInt32(avp.avp.code.toInt)
    //val flagsRaw:Seq[Byte] = (new ByteStringBuilder).putByte(avp.flags.value).result()
    val _flagsRaw:Seq[Byte] = Seq( ( if (avp.vendor.isDefined)
      avp.flags.value | new AvpFlags(Seq(AvpFlags.VendorSpecific)).value
      else
        avp.flags.value
      ).toByte )
    val _vendorRaw:Seq[Byte] = avp.avp.vendorid.fold(Seq.empty[Byte])(x => encodeInt32(x.code.toInt))
    val _dataRaw:Seq[Byte] = avp.dataRaw
    val _length = avp.dataRaw.length + (if (avp.vendor.isDefined) 12 else 8)
    //val lengthRaw:Seq[Byte] = (new ByteStringBuilder).putInt(length)(java.nio.ByteOrder.BIG_ENDIAN).result().drop(1)
    val _lengthRaw:Seq[Byte] = encodeInt32(_length).drop(1)
    val _paddingRaw:Seq[Byte] = (avp.dataRaw.length % 4) match {
      case 0 => Seq.empty[Byte]
      case 1 => Seq[Byte](0,0,0)
      case 2 => Seq[Byte](0,0)
      case 3 => Seq[Byte](0)
    }
    new EarlyAvp{
      override val bodyRaw: Seq[Byte] = _codeRaw ++ _flagsRaw ++ _lengthRaw ++ _vendorRaw ++ _dataRaw ++ _paddingRaw
    }
  }
  def decodeAvp(ea:EarlyAvp)(implicit dictionary:GenericDictionary):Avp = {
    implicit val byteOrder = java.nio.ByteOrder.BIG_ENDIAN

    val vend = if (ea.vendorId.isDefined)
      dictionary.collect({case v:DictionaryVendor => v}).find(_.code == ea.vendorId.get)
    else None

    val avpDict = dictionary.collect({case a:DictionaryAvp => a})
    val savps = avpDict.find( x => x.vendorid == vend && x.code == ea.code )

    savps match {
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.Integer32 => new AvpInteger32(flags = ea.flags,avp = avp, vendor = vend,new Integer32(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.UTF8String => new AvpUTFString(flags = ea.flags,avp = avp, vendor = vend,new UTFString(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.DiameterIdentity => new AvpDiameterIdentity(flags = ea.flags,avp = avp, vendor = vend,new UTFString(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.VendorId => new AvpVendorId(flags = ea.flags,avp = avp, vendor = vend,new Integer32(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.IPAddress => new AvpIPAddress(flags = ea.flags,avp = avp, vendor = vend,new IPAddress(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.Unsigned32 => new AvpUnsigned32(flags = ea.flags,avp = avp, vendor = vend,new Integer32(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.AppId => new AvpUnsigned32(flags = ea.flags,avp = avp, vendor = vend,new Integer32(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.Time => new AvpUnsigned32(flags = ea.flags,avp = avp, vendor = vend,new Integer32(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.OctetString => new AvpOctetString(flags = ea.flags,avp = avp, vendor = vend, new OctetString(ea.dataRaw))
      case Some(avp) if avp.typeName == DictionaryAvpTypeValue.Unsigned64 => new AvpUnsigned64(flags = ea.flags,avp = avp, vendor = vend,new Integer64(ea.dataRaw))
      //TODO: Add decoding of all types
      case Some(avp:DictionaryAvp with DictionaryAvpEnum) if avp.typeName == DictionaryAvpTypeValue.Enumerated => new AvpEnumerated(flags = ea.flags,avp = avp, vendor = vend,new Integer32(ea.dataRaw))
      case Some(avp:DictionaryAvp with DictionaryAvpGroup) if avp.typeName == DictionaryAvpTypeValue.Grouped => new AvpGrouped(flags = ea.flags,avp = avp, vendor = vend, new Group(ea.dataRaw))

      case Some(a) => new Avp {
        val flags:AvpFlags = ea.flags
        val vendor:Option[DictionaryVendor] = vend
        val avp:DictionaryAvp = a
        val dataRaw:Seq[Byte] = ea.dataRaw
      }
      case _ => throw new Exception("Unknown AVP code: " + ea.code + ", vendor: " + vend)
    }
  }
  def encodeAvpGroup(value:Seq[Avp]):Seq[Byte] = value.foldRight(Seq.empty[Byte])( encodeAvp(_).bodyRaw ++ _ )
  def decodeString(seq:Seq[Byte]):String = Converter.bytes2StringUTF(seq)
  def encodeString(value:String):Seq[Byte] = Converter.stringUTF2Bytes(value)
  def encodeInteger32(value:Int):Seq[Byte] = {encodeInt32(value)}
  def decodeInteger32(seq:Seq[Byte]):Int ={ seq.slice(0,4).foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF)) }
  def encodeInteger64(value:Long):Seq[Byte] = {encodeInt64(value)}
  def decodeInteger64(seq:Seq[Byte]):Long = { seq.slice(0,8).foldLeft(0)((a,b)=> (a << 8) + (b & 0xFF)) }
  def encodeIPAddress(value:InetAddress):Seq[Byte] = {
    (value match {
      case ip4:Inet4Address => Seq[Byte](0,1)
      case ip4:Inet6Address => Seq[Byte](0,2)
    }) ++ value.getAddress
  }
  def decodeIPAddress(seq:Seq[Byte]):InetAddress ={ InetAddress.getByAddress( seq.drop(2).toArray ) }
  def encodeOctetString(value:String):Seq[Byte] = if (value.startsWith("0x"))
    for (pair <- value.drop(2).filter(x=>"abcdef0123456789".contains(x.toLower)).toCharArray.grouped(2).map(ar => ar(0)->ar(1)).toList)
      yield ((Character.digit(pair._1, 16) << 4) + Character.digit(pair._2, 16)).toByte
  else
    Converter.stringASCII2Bytes(value)
  def decodeOctetString(seq:Seq[Byte]):String = Converter.bytes2hex(seq,None)
}

