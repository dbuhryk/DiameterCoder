package diameter.Dictionary

case class DictionaryAvpFlags(flagEncrypt:Boolean,
                              flagMadatory:DictionaryAvpFlagValue.Value,
                              flagProtected:DictionaryAvpFlagValue.Value,
                              flagVendorbit:DictionaryAvpFlagValue.Value) {
}