package org.pcarrasco.spiceworksapi.webresource;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Jersey resource configuration.
 */
public class SpiceworksApiResourceConfig extends ResourceConfig {
    public SpiceworksApiResourceConfig() {
        packages(true, "org.pcarrasco.spiceworksapi");
    }
}
