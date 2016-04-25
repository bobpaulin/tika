package org.apache.tika.detect;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

public class NoOpDetector implements Detector {

    /**
     * 
     */
    private static final long serialVersionUID = -8205444214329088385L;

    @Override
    public MediaType detect(InputStream input, Metadata metadata) throws IOException {
        return null;
    }

}
