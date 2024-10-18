package me.nzuguem.springnative;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import me.nzuguem.springnative.models.StartupMessageProperties;
import me.nzuguem.springnative.services.HelloService;

@SpringBootApplication
@EnableConfigurationProperties(StartupMessageProperties.class)
public class SpringNativeApplication {

	private final static HelloService HELLO_SERVICE_DEFAULT = () -> "Hello From Default Service";
	private final static HelloService HELLO_SERVICE_PROD = () -> "Hello From Prod Service";

	public static void main(String[] args) {
		SpringApplication.run(SpringNativeApplication.class, args);
	}

	@Bean
	CommandLineRunner startupRunner(StartupMessageProperties properties) {
		return args -> System.out.println(properties.message());
	}

	// If you don't specify the profile at the time of the AOT build (processAot), you can choose it dynamically at runtime.
	// @Bean
	HelloService helloService(Environment environment) {
		return Arrays.asList(environment.getActiveProfiles()).contains("prod") ? HELLO_SERVICE_PROD :  HELLO_SERVICE_DEFAULT;
	}

	@Bean
	@Profile("!prod")
	HelloService helloServiceDefault() {
		return HELLO_SERVICE_DEFAULT;
	}

	@Bean
	@Profile("prod")
	HelloService helloServiceProd() {
		return HELLO_SERVICE_PROD;
	}

}
