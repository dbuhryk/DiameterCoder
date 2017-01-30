package diameter

import diameter.Dictionary.{DictionaryApplication, DictionaryCommand, DictionaryObject, GenericDictionary}
import diameter.ValueContainers.Avp

/**
  * Created by edzmbuh on 04/04/2016.
  */

//trait AvpGroup extends AvpHeader with Iterable[AvpHeader]

case class Message(flag:CommandFlags, cmd:DictionaryCommand, app:DictionaryApplication, hbh:Int, ete:Int, avps:Seq[Avp]){
  implicit val byteOrder = java.nio.ByteOrder.BIG_ENDIAN
  val iterator = avps.iterator
  override def toString() = {cmd +" " + flag + " " + hbh + ":" + ete + " "+ avps.map(_.toString).mkString(",")}
  def toDebugString() = {"Application: "+app+"\n"+"Command: "+cmd +"\n" +"Flag: "+ flag + "\n" + "HbH:EtE: " + hbh + ":" + ete + avps.foldLeft("")(_+"\n"+_.toString())}
}

object Message {
  def apply(cmd:Any,app:Any,flag:Any,hbh:Int,ete:Int,avps:Avp*)(implicit dictionary:GenericDictionary):Message ={
    val _cmd = cmd match {
      case _cmd:Int => {
        dictionary.collect(
          { case x@Dictionary.DictionaryCommand(__cmd, _) if __cmd ==_cmd => x }:PartialFunction[DictionaryObject,DictionaryCommand]
        ).collectFirst({case x:DictionaryCommand => x}).get
      }
      case _cmd:String => {
        dictionary.collect(
          { case x@Dictionary.DictionaryCommand(_, __cmd) if __cmd ==_cmd => x }:PartialFunction[DictionaryObject,DictionaryCommand]
        ).collectFirst({case x:DictionaryCommand => x}).get
      }
      case _cmd:DictionaryCommand => _cmd
    }
    val _app = app match {
      case _app:Int => {
        dictionary.collectFirst(
          { case x@Dictionary.DictionaryApplication(__app, _) if __app ==_app => x }:PartialFunction[DictionaryObject,DictionaryApplication]
        ).get
      }
      case _app:String => {
        dictionary.collectFirst(
          { case x@Dictionary.DictionaryApplication(_, __app) if __app ==_app => x }:PartialFunction[DictionaryObject,DictionaryApplication]
        ).get
      }
      case _app:DictionaryApplication => _app
    }
    val _flag = flag match {
      case _flag:String => {
        new CommandFlags((if (_flag.toLowerCase.contains("r")) Seq(CommandFlags.Request) else Nil) ++
          (if (_flag.toLowerCase.contains("p")) Seq(CommandFlags.Proxiable) else Nil) ++
          (if (_flag.toLowerCase.contains("e")) Seq(CommandFlags.Error) else Nil) ++
          (if (_flag.toLowerCase.contains("t")) Seq(CommandFlags.Retransmission) else Nil))
      }
      case _flag:Int => new CommandFlags(_flag.toByte)
      case _flag:Byte => new CommandFlags(_flag)
      case _flag:CommandFlags => _flag
    }
    new Message(_flag,_cmd,_app,hbh,ete,avps)
  }

  //def commonWatchDogRequest(host:String,realm:String)(implicit dictionary:GenericDictionary) = {}
  //def commonWatchDogRespond(me:Message) = {}
}