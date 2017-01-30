package diameter.Dictionary.block

import java.io.{ByteArrayOutputStream, ObjectOutputStream}

import diameter.Dictionary._

import scala.io.BufferedSource


trait DictionaryBlockStorage extends GenericDictionary{


  object DictionaryObjectType extends Enumeration {
    val Avp,Command,Vendor,Application,Type = Value
  }

  def fromSource(source:BufferedSource) = {
    //val res = source.map(_.toByte).toArray
    //val br:BufferedReader = source.bufferedReader()
  }

  def serialize: Array[Byte] = {
    val byteOut = new ByteArrayOutputStream()
    val objOut = new ObjectOutputStream(byteOut)

    objOut.writeObject(iterator.length)

    iterator.foreach({
      case x:DictionaryAvp =>  {objOut.writeObject(DictionaryObjectType.Avp); objOut.writeObject(x)}
      case x:DictionaryApplication =>  {objOut.writeObject(DictionaryObjectType.Application); objOut.writeObject(x)}
      case x:DictionaryVendor =>  {objOut.writeObject(DictionaryObjectType.Vendor); objOut.writeObject(x)}
      case x:DictionaryCommand =>  {objOut.writeObject(DictionaryObjectType.Command); objOut.writeObject(x)}
    })

    objOut.close()
    byteOut.close()
    byteOut.toByteArray
  }

  def deserialize(bytes: Array[Byte]): Unit = {

  }
}
