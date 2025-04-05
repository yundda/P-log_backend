package com.example.plog.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;


@Configuration
public class DotenvConfig {
    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("MANAGER_PORT", dotenv.get("MANAGER_PORT"));
        System.setProperty("DB_DEV_USERNAME", dotenv.get("DB_DEV_USERNAME"));
        System.setProperty("DB_DEV_PASSWORD", dotenv.get("DB_DEV_PASSWORD"));
        System.setProperty("DB_DEV_URL", dotenv.get("DB_DEV_URL"));
        System.setProperty("JWT_ISSUER", dotenv.get("JWT_ISSUER"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
    }
}
