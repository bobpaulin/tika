/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.detect.Detector;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface NoOpServiceHandler {
    public static final Parser NOOP_PARSER = new Parser() {
        
        /**
         * 
         */
        private static final long serialVersionUID = -5333512419477858654L;

        @Override
        public void parse(InputStream stream, ContentHandler handler, Metadata metadata, ParseContext context)
                throws IOException, SAXException, TikaException {
        }
        
        @Override
        public Set<MediaType> getSupportedTypes(ParseContext context) {
            return Collections.emptySet();
        }
    };
    
    public static final Detector NOOP_DETECTOR = new Detector() {
        
        /**
         * 
         */
        private static final long serialVersionUID = 196394942829093670L;

        @Override
        public MediaType detect(InputStream input, Metadata metadata) throws IOException {
            return null;
        }
    };
    
    public static final EncodingDetector NOOP_ENCODING_DETECTOR = new EncodingDetector() {
        
        @Override
        public Charset detect(InputStream input, Metadata metadata) throws IOException {
            return null;
        }
    };
}
