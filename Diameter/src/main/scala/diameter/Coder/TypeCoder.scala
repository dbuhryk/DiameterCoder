package diameter.Coder

abstract class TypeCoder[T](data:Either[Seq[Byte],T]) {
  type A = T
  val encode:T=>Seq[Byte]
  val decode:Seq[Byte]=>T
  //def this(value:T) = this(Right(value))
  //def this(seq:Seq[Byte]) = this(Left(seq))
  def value:T = {
    data match {
      case Right(x) => x
      case Left(rawData) => decode(rawData)
    }
  }
  def dataRaw:Seq[Byte] = {
    data match {
      case Right(x) => encode(x)
      case Left(rawData) => rawData
    }
  }
}