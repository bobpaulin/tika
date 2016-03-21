module tika.core {
    requires java.logging;
    requires public org.osgi.core;
    requires public bndlib;
    requires public java.sql;
    requires public java.xml;
    requires public java.desktop;
    exports org.apache.tika;
    exports org.apache.tika.concurrent;
    exports org.apache.tika.config;
    exports org.apache.tika.detect;
    exports org.apache.tika.embedder;
    exports org.apache.tika.exception;
    exports org.apache.tika.extractor;
    exports org.apache.tika.fork;
    exports org.apache.tika.io;
    exports org.apache.tika.language.detect;
    exports org.apache.tika.language.translate;
    exports org.apache.tika.metadata;
    exports org.apache.tika.mime;
    exports org.apache.tika.osgi;
    exports org.apache.tika.osgi.internal;
    exports org.apache.tika.parser;
    exports org.apache.tika.parser.external;
    exports org.apache.tika.sax;
    exports org.apache.tika.sax.xpath;
    exports org.apache.tika.utils;
    
    uses org.apache.tika.parser.Parser;
    uses org.apache.tika.detect.Detector;
    uses org.apache.tika.detect.EncodingDetector;
    
}
