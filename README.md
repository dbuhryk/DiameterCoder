# DiameterCoder
Work in progress project.

Diameter message Encoder/Decoder

Main features:
* Coder follows [RFC 7633](https://tools.ietf.org/html/rfc6733)
* Dictionary can be loaded from [Wireshark](https://www.wireshark.org/) Diameter dictionary (`dictionary.xml` with all dependencies) 

Usage example1:
```
Message(
      cmd = "Device-Watchdog",
      app = "Diameter Common Messages",
      flag = "R",
      hbh = 1,
      ete = 1,
      AVP("Origin-Host","host1"),
      AVP("Origin-Realm","realm1")
    )
```

Usage example2:
```
Message(
      cmd = "Device-Watchdog",
      app = "Diameter Common Messages",
      flag = "",
      hbh = 1,
      ete = 1,
      AVP("Origin-Host","host1"),
      AVP("Origin-Realm","realm1")
      AVP("Result-Code",2001)
    )
```

AVP types available:
* Grouped
* Integer32
* IP Address
* Unsigned32 (via Integer32)
* UTF String
* Vendor (via Integer32)
* Diameter Identity (via UTF String)
* Enumerated (via Integer32)

Data type codecs available:
* Groupped AVP
* Enumerated AVP
* Integer32
* IP Address
* UTFString
