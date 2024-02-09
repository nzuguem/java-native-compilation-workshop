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

@Path("hello")
public class HelloController {

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("name") String name) {
        return "Hello " + name;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {

        try (var inputStream = getClass().getClassLoader().getResourceAsStream("hello.txt");
             var reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            var content = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            return Response.ok(content).build();
        } catch (IOException exception) {
            return Response.serverError().build();
        }
    }
}
