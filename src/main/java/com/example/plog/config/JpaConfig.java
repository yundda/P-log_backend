// package com.example.plog.config;

// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.JpaVendorAdapter;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
// import org.springframework.transaction.PlatformTransactionManager;

// import javax.sql.DataSource;
// import java.util.HashMap;
// import java.util.Map;

// @Configuration
// @EnableJpaRepositories(
//         basePackages = {"com.example.plog.repository.detaillog","com.example.plog.repository.family","com.example.plog.repository.healthlog",
//                 "com.example.plog.repository.pet","com.example.plog.repository.petlog","com.example.plog.repository.request","com.example.plog.repository.user"},
//         entityManagerFactoryRef = "entityManagerFactoryBean",
//         transactionManagerRef = "tmJpa"
// )
// @EntityScan(basePackages = {"com.example.plog.repository.detaillog","com.example.plog.repository.family","com.example.plog.repository.healthlog",
//                 "com.example.plog.repository.pet","com.example.plog.repository.petlog","com.example.plog.repository.request","com.example.plog.repository.user"})

// public class JpaConfig {

//     @Bean
//     public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("dataSource")DataSource dataSource){
//         LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//         em.setDataSource(dataSource);
//         em.setPackagesToScan("com.example.plog.repository.detaillog","com.example.plog.repository.family","com.example.plog.repository.healthlog",
//                 "com.example.plog.repository.pet","com.example.plog.repository.petlog","com.example.plog.repository.request","com.example.plog.repository.user");

//         JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//         em.setJpaVendorAdapter(vendorAdapter);

//         Map<String, Object> properties = new HashMap<>();
//         properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//         properties.put("hibernate.format_sql", "true");
//         properties.put("hibernate.use_sql_comment", "true");

//         em.setJpaPropertyMap(properties);
//         return em;
//     }

//     @Bean(name = "tmJpa")
//     public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource){
//         JpaTransactionManager transactionManager = new JpaTransactionManager();
//         transactionManager.setEntityManagerFactory(entityManagerFactoryBean(dataSource).getObject());
//         return transactionManager;
//     }

// }