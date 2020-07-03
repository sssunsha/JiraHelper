package com.hybris.caas;

import com.hybris.caas.service.JiraHelperMangerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.hybris.caas"})
public class JiraHelperApplication {

    public static void main(final String[] args) {
        System.out.println("Welcome to Jira helper ...........");
        new JiraHelperMangerService().start();
    }
}
