package com.tddrampup;

import com.google.inject.AbstractModule;
import com.tddrampup.factories.CameraUpdateFactoryWrapper;
import com.tddrampup.factories.CameraUpdateFactoryWrapperInterface;
import com.tddrampup.factories.MarkerOptionsFactoryWrapper;
import com.tddrampup.factories.MarkerOptionsFactoryWrapperInterface;

/**
 * Created by: WX009-PC
 * on: 2/24/14.
 */
public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CameraUpdateFactoryWrapperInterface.class).to(CameraUpdateFactoryWrapper.class);
        bind(MarkerOptionsFactoryWrapperInterface.class).to(MarkerOptionsFactoryWrapper.class);
    }
}
