package me.nzuguem.springnative;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping("hello")
@ImportRuntimeHints(HelloController.HelloControllerRuntimeHints.class) // For Native Image
public class HelloController {

    @Value("classpath:hello.txt")
    private Resource resource;

    @GetMapping(value = "{name}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> hello() {
        try {
            return ResponseEntity.ok(this.resource.getContentAsString(Charset.defaultCharset()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    // Register Resources Files for Native Image via RuntimeHints
    static class HelloControllerRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("hello.txt");
        }
    }
}
