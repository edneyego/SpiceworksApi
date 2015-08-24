package org.pcarrasco.spiceworksapi.utils;

import com.google.inject.AbstractModule;
import org.pcarrasco.spiceworksapi.persistence.SpiceworksApiUnitOfWork;
import org.pcarrasco.spiceworksapi.persistence.SpiceworksApiUnitOfWorkImpl;
import org.pcarrasco.spiceworksapi.service.SpiceworksApiService;
import org.pcarrasco.spiceworksapi.service.SpiceworksApiServiceImpl;

/**
 * Class that defines the actual implementations of injected interfaces for
 * Guide dependency injection framework
 */
public class SpiceworksApiAbstractModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SpiceworksApiUnitOfWork.class).to(SpiceworksApiUnitOfWorkImpl.class);
        bind(SpiceworksApiService.class).to(SpiceworksApiServiceImpl.class);
    }
}
