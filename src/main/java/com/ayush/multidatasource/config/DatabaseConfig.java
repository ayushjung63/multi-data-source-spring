package com.ayush.multidatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EntityScan
public class DatabaseConfig {

    @Value("${spring.datasource.read.url}")
    private String readUrl;

    @Value("${spring.datasource.write.url}")
    private String witeUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @ConfigurationProperties(prefix = "db.write")
    public DataSource readWriteConfiguration() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(witeUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "db.read")
    public DataSource readOnlyConfiguration(){
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(readUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    @Primary
    public DataSource routingDataSource() {
        return new TransactionRoutingDataSource(readOnlyConfiguration(),readWriteConfiguration());
        //loggingProxy("read_only", readOnlyConfiguration()));
    }

//    private DataSource loggingProxy(String name, DataSource dataSource) {
//        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
//        loggingListener.setLogLevel(SLF4JLogLevel.DEBUG);
//        loggingListener.setLogger(name);
//        loggingListener.setWriteConnectionId(false);
//        return ProxyDataSourceBuilder.create(dataSource).name(name).listener(loggingListener).build();
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(routingDataSource()).packages("com.ayush.multidatasource.model").build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("jpaTxManager") PlatformTransactionManager wrapped) {
        return new ReplicaAwareTransactionManager(wrapped);
    }

    @Bean(name = "jpaTxManager")
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}