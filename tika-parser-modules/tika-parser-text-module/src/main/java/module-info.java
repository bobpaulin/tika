module tika.parser.text.module {
    requires public java.xml;
    requires tika.core;
    requires juniversalchardet;
    requires commons.io;
    requires commons.codec;
    requires commons.logging;
    exports org.apache.tika.parser.strings;
    exports org.apache.tika.parser.txt;
    exports org.apache.tika.parser.xml;
    provides org.apache.tika.detect.EncodingDetector with org.apache.tika.parser.txt.Icu4jEncodingDetector;
    provides org.apache.tika.detect.EncodingDetector with org.apache.tika.parser.txt.UniversalEncodingDetector;
    provides org.apache.tika.parser.Parser with org.apache.tika.parser.txt.TXTParser;
    provides org.apache.tika.parser.Parser with org.apache.tika.parser.xml.DcXMLParser;
    provides org.apache.tika.parser.Parser with org.apache.tika.parser.xml.FictionBookParser;
}
