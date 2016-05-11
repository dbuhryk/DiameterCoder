package Diameter.Templates

import Diameter.Coder.{Avp, AvpValue}
import Diameter.Dictionary.{DictionaryApplication, DictionaryCommand, DictionaryObject, GenericDictionary}
import Diameter.{CommandFlags, Message}

/**
  * Created by dmitribugrik on 17/04/16.
  */
object Message {
  def apply(cmd:Any,app:Any,flag:Any,hbh:Int,ete:Int,avps:Avp with AvpValue*)(implicit dictionary:GenericDictionary):Message ={
    val _cmd = cmd match {
      case _cmd:Int => {
        dictionary.collect(
          { case x@DictionaryCommand(__cmd, _) if __cmd ==_cmd => x }:PartialFunction[DictionaryObject,DictionaryCommand]
        ).collectFirst({case x:DictionaryCommand => x}).get
      }
      case _cmd:String => {
        dictionary.collect(
          { case x@DictionaryCommand(_, __cmd) if __cmd ==_cmd => x }:PartialFunction[DictionaryObject,DictionaryCommand]
        ).collectFirst({case x:DictionaryCommand => x}).get
      }
      case _cmd:DictionaryCommand => _cmd
    }
    val _app = app match {
      case _app:Int => {
        dictionary.collectFirst(
          { case x@DictionaryApplication(__app, _) if __app ==_app => x }:PartialFunction[DictionaryObject,DictionaryApplication]
        ).get
      }
      case _app:String => {
        dictionary.collectFirst(
          { case x@DictionaryApplication(_, __app) if __app ==_app => x }:PartialFunction[DictionaryObject,DictionaryApplication]
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
