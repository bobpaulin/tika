package org.apache.tika.detect;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.config.LoadErrorHandler;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

public class ProxyDetector implements Detector{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4534101565629801667L;
    private Detector detector;
    
    public ProxyDetector(String detectorClassName) {
        this(detectorClassName, LoadErrorHandler.WARN);
    }
    
    public ProxyDetector(String detectorClassName, LoadErrorHandler handler) {
        try {
            this.detector = (Detector)Class.forName(detectorClassName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            handler.handleLoadError(detectorClassName, e);
        }
    }

    @Override
    public MediaType detect(InputStream input, Metadata metadata) throws IOException {
        if(detector != null)
        {
            return detector.detect(input, metadata);
        }
        return null;
    }

}
