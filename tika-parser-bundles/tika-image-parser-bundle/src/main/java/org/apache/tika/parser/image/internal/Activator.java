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
package org.apache.tika.parser.image.internal;

import java.util.Dictionary;
import java.util.Properties;

import org.apache.tika.parser.Parser;
import org.apache.tika.parser.image.ImageParser;
import org.apache.tika.parser.jpeg.JpegParser;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        
        Dictionary imageProps = new Properties();
        String imageServiceRank = context.getProperty("org.apache.tika.parser.image.serviceRank");
        if (imageServiceRank != null) {
            imageProps.put(Constants.SERVICE_RANKING, Integer.parseInt(imageServiceRank));
        }

        Dictionary jpegProps = new Properties();
        String jpegServiceRank = context.getProperty("org.apache.tika.parser.jpeg.serviceRank");
        if (jpegServiceRank != null) {
            jpegProps.put(Constants.SERVICE_RANKING, Integer.parseInt(jpegServiceRank));
        }

        context.registerService(Parser.class, new JpegParser(), jpegProps);
        context.registerService(Parser.class, new ImageParser(), imageProps);

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub

    }

}
