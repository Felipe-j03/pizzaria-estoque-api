package com.nostrapizza.estoque_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugConfig {

    @Value("${spring.datasource.url:NOT_SET}")
    private String url;

    @Value("${spring.datasource.username:NOT_SET}")
    private String username;

    @Value("${spring.datasource.password:NOT_SET}")
    private String password;

    @Bean
    public CommandLineRunner debug() {
        return args -> {
            System.out.println("=========================================");
            System.out.println("URL: [" + url + "]");
            System.out.println("USERNAME: [" + username + "] (length=" + username.length() + ")");
            System.out.println("PASSWORD: [" + password + "] (length=" + password.length() + ")");
            System.out.print("Password chars: ");
            for (char c : password.toCharArray()) {
                System.out.print("[" + c + "=" + (int) c + "] ");
            }
            System.out.println();
            System.out.println("=========================================");
        };
    }
}