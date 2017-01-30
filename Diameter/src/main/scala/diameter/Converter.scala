package diameter

import java.util.TimeZone

import akka.util.ByteString

object Converter {
  def bytes2hex(bytes: Seq[Byte], sep: Option[String] = Some(" ")): String = {
    if (!bytes.isEmpty) {
      val str1 = sep match {
        case None => bytes.take(bytes.length - 1).map("%02x".format(_)).mkString
        case _ => bytes.take(bytes.length - 1).map("%02x".format(_) + sep.get).mkString
      }
      val str2 = bytes.takeRight(1).map("%02x".format(_)).mkString
      str1 + str2
    } else ""
  }
  def bytes2hexLen(bytes: Seq[Byte], len:Int=16, sep: Option[String] = Some(" ")): String = {
    (if (bytes.length>len) "("+len+"/"+bytes.length+")" else "("+bytes.length+")") +
      "["+ bytes2hex(bytes.take(len), sep) +
      (if (bytes.length>len) "..." else "") +
      "]"
  }
  def bytes2StringUTF(seq:Seq[Byte]):String = {ByteString(seq.toArray).decodeString("UTF-8")}
  def stringUTF2Bytes(value:String):Seq[Byte] = {ByteString.fromString(value, "UTF-8")}
  def stringASCII2Bytes(value:String):Seq[Byte] = ByteString.fromString(value, "ASCII")
  def string2Time(value:String):Int = {
    val format = value.substring(value.indexOf("[") + 1, value.indexOf("]"))
    val datetime = value.substring(value.indexOf("]")+1)
    val dateformat = new java.text.SimpleDateFormat(format)
    dateformat.setTimeZone(TimeZone.getTimeZone("GMT"))
    val res = dateformat.parse(datetime)
    (res.getTime/1000 + 2208988800L).toInt
  }
}
