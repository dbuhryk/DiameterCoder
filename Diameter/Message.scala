package Diameter

import Diameter.Coder.{Avp, AvpValue}
import Diameter.Dictionary.{DictionaryApplication, DictionaryCommand}

/**
  * Created by edzmbuh on 04/04/2016.
  */

//trait AvpGroup extends Avp with Iterable[Avp]

case class Message(flag:CommandFlags, cmd:DictionaryCommand, app:DictionaryApplication, hbh:Int, ete:Int, avps:Seq[Avp with AvpValue]){
  implicit val byteOrder = java.nio.ByteOrder.BIG_ENDIAN
  val iterator = avps.iterator
  override def toString() = {cmd +" " + flag + " " + hbh + ":" + ete + " "+ avps.foldLeft("")(_+_.toString())}
  def toDebugString() = {"Application: "+app+"\n"+"Command: "+cmd +"\n" +"Flag: "+ flag + "\n" + "HbH:EtE: " + hbh + ":" + ete + avps.foldLeft("")(_+"\n"+_.toString())}
}
