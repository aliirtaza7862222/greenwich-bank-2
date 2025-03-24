package com.bank.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

// @ApplicationPath defines the base URI for all JAX-RS resources.
@ApplicationPath("/api")
public class RestApplication extends Application {
    // No methods are needed here unless you want to override defaults.
}
