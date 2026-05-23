package com.akademik.sisko.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String defaultUrl;

    @Value("${spring.datasource.username}")
    private String defaultUsername;

    @Value("${spring.datasource.password}")
    private String defaultPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String defaultDriver;

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // Check if DATABASE_URL env (injected by Railway PostgreSQL) is available
        String dbUrlEnv = System.getenv("DATABASE_URL");
        if (dbUrlEnv != null && (dbUrlEnv.startsWith("postgres://") || dbUrlEnv.startsWith("postgresql://"))) {
            try {
                // Parse format: postgres://user:password@host:port/database
                URI dbUri = new URI(dbUrlEnv);
                String userInfo = dbUri.getUserInfo();
                
                String username = userInfo.split(":")[0];
                String password = userInfo.split(":")[1];
                
                // Construct standard Spring JDBC postgresql URL
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

                dataSource.setDriverClassName("org.postgresql.Driver");
                dataSource.setUrl(dbUrl);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                
                System.out.println(">>> DatabaseConfig: Successfully configured PostgreSQL DataSource from DATABASE_URL.");
                return dataSource;
            } catch (Exception e) {
                System.err.println(">>> DatabaseConfig: Failed to parse DATABASE_URL environment variable: " + e.getMessage());
                System.err.println(">>> DatabaseConfig: Falling back to application.properties configuration.");
            }
        }

        // Fallback to application.properties defaults (local H2 file-based)
        dataSource.setDriverClassName(defaultDriver);
        dataSource.setUrl(defaultUrl);
        dataSource.setUsername(defaultUsername);
        dataSource.setPassword(defaultPassword);
        
        System.out.println(">>> DatabaseConfig: Using default DataSource configuration: " + defaultUrl);
        return dataSource;
    }
}
