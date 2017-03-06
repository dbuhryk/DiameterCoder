# DiameterCoder
Work in progress project.

Diameter message binarry Encoder/Decoder

Main features:
* Coder follows [RFC 7633](https://tools.ietf.org/html/rfc6733)
* Dictionary can be loaded from [Wireshark](https://www.wireshark.org/) Diameter dictionary (`dictionary.xml` with all dependencies)
* Encoded binarry array can be directly inserted into TCP payload.
* The sole purpose of the project to build the coder. Implementation of TCP stack functions such as sending and receiving TCP frames, handling TCP links etc. are entirelly out of the scope.

AVP basic types:
* Grouped
* Enumerated
* Integer32
* Integer64
* OctetString

AVP Extended types:
* UTF String
* Unsigned32
* Unsigned64
* IP Address
* Vendor
* Diameter Identity
* Time
* Application Id
* Vendor Id

Main working classes and traits:

* Avp
* EarlyAvp 
* Message
* EarlyMessage

`Avp` and `Message` are standard Diameter absractions.
`EarlyAvp` and `EarlyMessage` implement lazy encoding/decoding from `Seq[Byte]` to `Message` and `Avp` and back.
`Message.apply` and `AVP.apply` implement flexible instatiation of `Message` and `Avp` stereotypes

Usage example1:
```
val msg =  Message(
      cmd = "Device-Watchdog",
      app = "Diameter Common Messages",
      flag = "R",
      hbh = 1,
      ete = 1,
      AVP("Origin-Host","host1"),
      AVP("Origin-Realm","realm1")
    )
val earlyMessage = DiameterCoder.encodeMessage(msg)
val bytes = emsg.bodyRaw
```

Usage example2:
```
val sample = Seq[Byte](...)
val earlyMessage = DiameterCoder.decodeEarlyMessage(sample)
val message = DiameterCoder.decodeMessage(earlyMessage)
```

Usage example3:
```
val sampleHexString = "0x" +
"01000080800001010..." //this part is a TCP payload "Diameter Protocol" pasted from wireshark via Copy -> ... as a Hex Stream
val sample = DiameterCoder.encodeOctetString (sampleHexString)
val earlyMessage = DiameterCoder.decodeEarlyMessage(sample)
val message = DiameterCoder.decodeMessage(earlyMessage)
```

Usage example4:
```
val sampleHexString = "0x" +
"0000010c4000000c000007d1" //this part is a AVP picked from TCP payload "Diameter Protocol" pasted from wireshark via Copy -> ... as a Hex Stream
val sample = ByteString(DiameterCoder.encodeOctetString(sampleHexString).toArray)
val earlyAvp = DiameterCoder.decodeEarlyAvp(sample)
val avp = DiameterCoder.decodeAvp(earlyAvp)
```
