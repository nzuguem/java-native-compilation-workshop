package me.nzuguem.springnative.models;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("hello.startup")
public record StartupMessageProperties(String message) {
}
