package com.example.plog;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
@EnableJpaAuditing
public class PlogApplication {
	
	public static void main(String[] args) {
        // Environment env = context.getEnvironment();
        // String profile = env.getProperty("spring.profiles.active");
        // String profile = System.getenv("SPRING_PROFILES_ACTIVE");
        // System.out.println(profile);
        // if("local".equals(profile)){
            Dotenv dotenv = Dotenv.configure().directory("/home/ubuntu/p-log/P-log_backend/deploy").ignoreIfMissing().load();
            // Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
            System.setProperty("MANAGER_PORT", dotenv.get("MANAGER_PORT"));
            // System.setProperty("DB_DEV_USERNAME", dotenv.get("DB_DEV_USERNAME"));
            // System.setProperty("DB_DEV_PASSWORD", dotenv.get("DB_DEV_PASSWORD"));
            // System.setProperty("DB_DEV_URL", dotenv.get("DB_DEV_URL"));
            System.setProperty("DB_PROD_USERNAME", dotenv.get("DB_PROD_USERNAME"));
            System.setProperty("DB_PROD_PASSWORD", dotenv.get("DB_PROD_PASSWORD"));
            System.setProperty("DB_PROD_URL", dotenv.get("DB_PROD_URL"));
            System.setProperty("JWT_ISSUER", dotenv.get("JWT_ISSUER"));
            System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
            System.setProperty("AWS_S3_ACCESS_KEY", dotenv.get("AWS_S3_ACCESS_KEY"));
            System.setProperty("AWS_S3_SECRET_ACCESS_KEY", dotenv.get("AWS_S3_SECRET_ACCESS_KEY"));
            System.setProperty("AWS_S3_REGION", dotenv.get("AWS_S3_REGION"));
            System.setProperty("AWS_S3_BUCKET", dotenv.get("AWS_S3_BUCKET"));

            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        // }else{
        //     Dotenv dotenv = Dotenv.configure().directory("/home/ubuntu/p-log/P-log_backend/deploy").ignoreIfMissing().load();
        //     System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        //     System.setProperty("MANAGER_PORT", dotenv.get("MANAGER_PORT"));
        //     // System.setProperty("DB_DEV_USERNAME", dotenv.get("DB_DEV_USERNAME"));
        //     // System.setProperty("DB_DEV_PASSWORD", dotenv.get("DB_DEV_PASSWORD"));
        //     // System.setProperty("DB_DEV_URL", dotenv.get("DB_DEV_URL"));
        //     System.setProperty("DB_PROD_USERNAME", dotenv.get("DB_PROD_USERNAME"));
        //     System.setProperty("DB_PROD_PASSWORD", dotenv.get("DB_PROD_PASSWORD"));
        //     System.setProperty("DB_PROD_URL", dotenv.get("DB_PROD_URL"));
        //     System.setProperty("JWT_ISSUER", dotenv.get("JWT_ISSUER"));
        //     System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        // System.setProperty("AWS_S3_ACCESS_KEY", dotenv.get("AWS_S3_ACCESS_KEY"));
        // System.setProperty("AWS_S3_SECRET_ACCESS_KEY", dotenv.get("AWS_S3_SECRET_ACCESS_KEY"));
        // System.setProperty("AWS_S3_REGION", dotenv.get("AWS_S3_REGION"));
        // System.setProperty("AWS_S3_BUCKET", dotenv.get("AWS_S3_BUCKET"));
        //     TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        // }

		SpringApplication.run(PlogApplication.class, args);

	}

}
