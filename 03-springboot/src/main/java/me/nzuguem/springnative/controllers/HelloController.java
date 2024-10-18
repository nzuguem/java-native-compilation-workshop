package me.nzuguem.springnative.controllers;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.nzuguem.springnative.models.Hello;
import me.nzuguem.springnative.services.HelloService;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("hello")
@ImportRuntimeHints(HelloController.HelloControllerRuntimeHints.class) // For Native Image
public class HelloController {

    @Value("classpath:hello.txt")
    private Resource resource;

    private final HelloService helloService;

    HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping(value = "{name}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String helloFromInput(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping(value = "from/service", produces = MediaType.TEXT_PLAIN_VALUE)
    public String helloFromService() {
        return this.helloService.hello();
    }

    @GetMapping(value = "from/file", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity helloFromFile() {
        try {
            return ResponseEntity.ok(this.resource.getContentAsString(Charset.defaultCharset()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(this.build5xxProblemDetailResponse(e));
        }
    }

    @GetMapping(value = "from/reflection", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity helloFromReflection() {
        try {
            return ResponseEntity.ok(this.getHelloMessageFromReflection());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.build5xxProblemDetailResponse(e));
        }
    }

    // We use Reflection to access the Hello class. 
    // AND without this annotation, it will not be present in the generated binary, because it cannot be accessed by static analysis
    
    // It is also possible to use the RuntimeHints API to do this
    // hints.reflection().registerType(Hello.class, MemberCategory.values())

    @RegisterReflectionForBinding(Hello.class)
    private String getHelloMessageFromReflection() throws Exception{

        var className = List.<String>of("me", "nzuguem", "springnative", "Hello")
            .stream().collect(Collectors.joining("."));

        return (String) Class.forName(className).getDeclaredField("MESSAGE").get(String.class);
    }

    private ProblemDetail build5xxProblemDetailResponse(Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }


    // Register Resources Files for Native Image via RuntimeHints
    static class HelloControllerRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("hello.txt");
            // hints.reflection().registerType(Hello.class, MemberCategory.values());
        }
    }
}
