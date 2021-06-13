package com.example.project.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.project.repository"})
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DataJpaConfig {
    private static final String PROP_DATABASE_DRIVER = "c3p0.driverClass";
    private static final String PROP_DATABASE_PASSWORD = "c3p0.password";
    private static final String PROP_DATABASE_URL = "c3p0.url";
    private static final String PROP_DATABASE_USERNAME = "c3p0.user";
    private static final String PROP_DATABASE_MIN_POOL_SIZE = "c3p0.minPoolSize";
    private static final String PROP_DATABASE_MAX_POOL_SIZE = "c3p0.maxPoolSize";
    private static final String PROP_DATABASE_MAX_IDLE_TIME = "c3p0.maxIdleTime";
    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROP_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROP_HIBERNATE_DLL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String ENTITIES_TO_SCAN = "com.example.project.entity";

    private final Environment env;

    @Autowired
    public DataJpaConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setDriverClass(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        pooledDataSource.setUser(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        pooledDataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        pooledDataSource.setJdbcUrl(env.getRequiredProperty(PROP_DATABASE_URL));
        pooledDataSource.setMinPoolSize(Integer.parseInt(env.getRequiredProperty(PROP_DATABASE_MIN_POOL_SIZE)));
        pooledDataSource.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty(PROP_DATABASE_MAX_POOL_SIZE)));
        pooledDataSource.setMaxIdleTime(Integer.parseInt(env.getRequiredProperty(PROP_DATABASE_MAX_IDLE_TIME)));

        return pooledDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(ENTITIES_TO_SCAN);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        Properties properties = new Properties();
        properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        properties.put(PROP_FORMAT_SQL, env.getRequiredProperty(PROP_FORMAT_SQL));
        properties.put(PROP_HIBERNATE_DLL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_DLL_AUTO));
        entityManagerFactoryBean.setJpaProperties(properties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
