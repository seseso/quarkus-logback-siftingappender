package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Path("/hello")
public class GreetingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);
    
    @Inject
    ManagedExecutor exec;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        
        exec.submit(() -> {
            String threadName = Thread.currentThread().getName();
            MDC.put("logFileName", "sifting_" + threadName + ".log");
            LOGGER.info("Hello Logback for thread {}", threadName);
            MDC.remove("logFileName");
        });
        
        
        LOGGER.info("Hello Logback");
        return "Hello RESTEasy";
    }
}