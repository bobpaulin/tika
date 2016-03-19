package org.apache.tika.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.config.LoadErrorHandler;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class ProxyParser extends AbstractParser {

    /**
     * 
     */
    private static final long serialVersionUID = -4838436708916910179L;
    private Parser parser;
    
    public ProxyParser(String parserClassName) {
        this(parserClassName, LoadErrorHandler.WARN);
    }
    
    public ProxyParser(String parserClassName, LoadErrorHandler handler) {
            try {
                this.parser = (Parser)Class.forName(parserClassName).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                handler.handleLoadError(parserClassName, e);
            }
        
    }
    
    @Override
    public Set<MediaType> getSupportedTypes(ParseContext context) {
        if (parser == null)
        {
            return Collections.emptySet();
        }
        return parser.getSupportedTypes(context);
    }

    @Override
    public void parse(InputStream stream, ContentHandler handler, Metadata metadata, ParseContext context)
            throws IOException, SAXException, TikaException {
        if(parser != null)
        {
            parser.parse(stream, handler, metadata, context);
        }
        //Otherwise do nothing
    }

}
