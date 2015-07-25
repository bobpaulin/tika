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
import static org.ops4j.pax.exam.CoreOptions.frameworkProperty;

import javax.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;

import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.osgi.TikaService;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRParser;
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
import org.xml.sax.ContentHandler;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class BundleIT {

    private final File TARGET = new File("target");
    @Inject
    private BundleContext bc;

    @Configuration
    public Option[] configuration() throws IOException, URISyntaxException {
        return options(frameworkProperty("org.apache.tika.parser.ocr.serviceRank").value("1"), junitBundles(),
                mavenBundle("org.apache.tika", "tika-osgi-bundle"),
                mavenBundle("org.apache.tika", "tika-image-parser-bundle"),
                bundle(new File(TARGET, "tika-tesseract-parser-bundle-1.10-SNAPSHOT.jar").toURI().toURL().toString()));
    }

    @Test
    public void testBundleLoaded() throws Exception {
        boolean hasCore = false, hasBundle = false;
        for (Bundle b : bc.getBundles()) {
            if ("org.apache.tika.osgi-bundle".equals(b.getSymbolicName())) {
                hasCore = true;
                assertEquals("Core not activated", Bundle.ACTIVE, b.getState());
            }
            if ("org.apache.tika.tesseract-parser-bundle".equals(b.getSymbolicName())) {
                hasBundle = true;
                assertEquals("Bundle not activated", Bundle.ACTIVE, b.getState());
            }
        }
        assertTrue("Core bundle not found", hasCore);
        assertTrue("Tesseract bundle not found", hasBundle);
    }

    @Test
    public void testOcrParser() throws Exception {

        TesseractOCRParser tessParser = new TesseractOCRParser();
        ParseContext context = new ParseContext();
        if(tessParser.getSupportedTypes(context).size() > 0)
        {
            TikaService tikaService = bc.getService(bc.getServiceReference(TikaService.class));
            InputStream stream = bc.getBundle().getResource("image15.png").openStream();

            assertNotNull(stream);

            Metadata metadata = new Metadata();
            TikaInputStream tikaStream = TikaInputStream.get(stream);
            MediaType type = tikaService.detect(tikaStream, metadata);

            assertEquals("Media Type should be PNG", MediaType.image("png"), type);

            metadata.add(Metadata.CONTENT_TYPE, type.toString());
            Writer writer = new StringWriter();
            ContentHandler contentHandler = new BodyContentHandler(writer);

            tikaService.parse(tikaStream, contentHandler, metadata, context);
            assertTrue("OCR Text should match image text", contentHandler.toString().trim().contains("Everything"));
        }
        else
        {
            System.out.println("Tesseract Not Installed Tests Not run.");
        }
        
    }

}
