package me.nzuguem.quarkusnative;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.common.annotation.RunOnVirtualThread;

@Path("hello")
public class HelloController {

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String hello(@PathParam("name") String name) {

        Log.infof("say hello %s", name);

        return "Hello " + name;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread // No interest in CPU Bound workloads
    public Response hello() {

        try (var inputStream = getClass().getClassLoader().getResourceAsStream("hello.txt");
             var reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            var content = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            
            Log.info("say hello from file");
        
            return Response.ok(content).build();
        } catch (IOException exception) {

            Log.error(exception.getMessage(), exception);

            return Response.serverError().build();
        }
    }
}
