package org.apache.tika.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class NoOpParser extends AbstractParser {

    /**
     * 
     */
    private static final long serialVersionUID = -1390166637373650916L;

    @Override
    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return Collections.EMPTY_SET;
    }

    @Override
    public void parse(InputStream stream, ContentHandler handler, Metadata metadata, ParseContext context)
            throws IOException, SAXException, TikaException {

    }

}
