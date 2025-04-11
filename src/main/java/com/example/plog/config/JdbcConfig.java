// package com.example.plog.config;


// import javax.sql.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.datasource.DataSourceTransactionManager;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;
// import org.springframework.transaction.PlatformTransactionManager;

// @Configuration
// public class JdbcConfig {
//     @Bean
//     public DataSource dataSource(){
//         DriverManagerDataSource dataSource = new DriverManagerDataSource();
//         dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//         dataSource.setUrl("jdbc:mysql://localhost:3306/pLog?useUnicode=true&characterEncoding=UTF-8");
//         dataSource.setUsername("root");
//         dataSource.setPassword("1111");
//         return dataSource;
//     }

//     @Bean
//     public JdbcTemplate jdbcTemplate(){
//         return new JdbcTemplate(dataSource());
//     }

//     @Bean(name = "tm1")
//     public PlatformTransactionManager transactionManager(){
//         return new DataSourceTransactionManager(dataSource());
//     }

// }
 