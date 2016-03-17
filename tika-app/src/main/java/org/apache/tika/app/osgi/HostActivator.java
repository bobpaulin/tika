package org.apache.tika.app.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HostActivator implements BundleActivator {
    
    private BundleContext context = null;

    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        this.context = null;

    }
    
    public Bundle[] getBundles()
    {
        if (context != null)
        {
            return context.getBundles();
        }
        return null;
    }
    
    public BundleContext getContext()
    {
        return this.context;
    }

}
