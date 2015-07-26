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
package org.apache.tika.bundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import javax.inject.Inject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.fork.ForkParser;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.osgi.TikaService;
import org.apache.tika.parser.CompositeParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.image.ImageParser;
import org.apache.tika.parser.internal.Activator;
import org.apache.tika.parser.jpeg.JpegParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.xml.sax.ContentHandler;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class BundleIT {

    private static final String BUNDLE_JAR_SYS_PROP = "project.bundle.file";
    @Inject
    private BundleContext bc;

    @Configuration
    public Option[] configuration() throws IOException, URISyntaxException {
        String bundleFileName = System.getProperty(BUNDLE_JAR_SYS_PROP);
        return options(
                junitBundles(),
                mavenBundle("org.apache.tika", "tika-osgi-bundle"),
                bundle(new File(bundleFileName).toURI().toString()));
    }


    @Test
    public void testBundleLoaded() throws Exception {
        boolean hasCore = false, hasBundle = false;
        for (Bundle b : bc.getBundles()) {
            if ("org.apache.tika.osgi-bundle".equals(b.getSymbolicName())) {
                hasCore = true;
                assertEquals("Core not activated", Bundle.ACTIVE, b.getState());
            }
            if ("org.apache.tika.image-parser-bundle".equals(b.getSymbolicName())) {
                hasBundle = true;
                assertEquals("Bundle not activated", Bundle.ACTIVE, b.getState());
            }
        }
        assertTrue("Core bundle not found", hasCore);
        assertTrue("Image bundle not found", hasBundle);
    }
    
    @Test
    public void testImageParser() throws Exception {
    	TikaService tikaService = bc.getService(bc.getServiceReference(TikaService.class));
    	InputStream stream = bc.getBundle().getResource("tika.png").openStream();
    	
    	assertNotNull(stream);
    	
    	Metadata metadata = new Metadata();
    	TikaInputStream tikaStream = TikaInputStream.get(stream);
    	MediaType type = tikaService.detect(tikaStream, metadata);
    	
    	assertEquals("Media Type should be PNG", MediaType.image("png"), type);
    	
    	metadata.add(Metadata.CONTENT_TYPE, type.toString());
    	Writer writer = new StringWriter();
        ContentHandler contentHandler = new BodyContentHandler(writer);
        ParseContext context = new ParseContext();
        
    	tikaService.parse(tikaStream, contentHandler, metadata, context);
    	
    	assertEquals("Image Output Width Should Match", "292", metadata.get(Metadata.IMAGE_WIDTH));
    }
    
    @Test
    public void testJpegParser() throws Exception {
    	
    	TikaService tikaService = bc.getService(bc.getServiceReference(TikaService.class));
    	InputStream stream = bc.getBundle().getResource("hadoop.jpg").openStream();
    	
    	assertNotNull(stream);
    	
    	Metadata metadata = new Metadata();
    	TikaInputStream tikaStream = TikaInputStream.get(stream);
    	MediaType type = tikaService.detect(tikaStream, metadata);
    	
    	assertEquals("Media Type should be JPEG", MediaType.image("jpeg"), type);
    	
    	metadata.add(Metadata.CONTENT_TYPE, type.toString());
    	Writer writer = new StringWriter();
        ContentHandler contentHandler = new BodyContentHandler(writer);
        ParseContext context = new ParseContext();
        
    	tikaService.parse(tikaStream, contentHandler, metadata, context);
    	
    	assertEquals("Jpg Output Width Should Match", "212", metadata.get(Metadata.IMAGE_WIDTH));
    }

}
