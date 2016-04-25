package org.apache.tika.detect;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.tika.metadata.Metadata;

public class NoOpEncodingDetector implements EncodingDetector {

    @Override
    public Charset detect(InputStream input, Metadata metadata) throws IOException {
        return null;
    }

}
