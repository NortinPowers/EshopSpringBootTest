//package by.tms.eshop.config;
//
//import lombok.RequiredArgsConstructor;
//import org.hibernate.SessionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Objects;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
//@RequiredArgsConstructor
//public class HibernateConfig {
//
//    private final Environment environment;
//
////    @Bean
////    public LocalSessionFactoryBean sessionFactory() {
////        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
////        sessionFactory.setDataSource(dataSource());
////        sessionFactory.setPackagesToScan("by.tms.eshop.model");
////        sessionFactory.setHibernateProperties(hibernateProperties());
////        return sessionFactory;
////    }
////
////    @Bean
////    public DataSource dataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//////        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.tomcat.driver-class-name")));
////        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
////        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
////        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
////        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
////        return dataSource;
////    }
////
////    @Bean
////    public PlatformTransactionManager hibernateTransactionManager() {
////        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
////        transactionManager.setSessionFactory(sessionFactory().getObject());
////        return transactionManager;
////    }
////
////    private Properties properties() {
////        Properties properties = new Properties();
////        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
////        properties.setProperty("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
////        properties.setProperty("hibernate.show_sql", environment.getProperty("spring.jpa.properties.hibernate.show_sql"));
////        return properties;
////    }
////
////    @Bean
////    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
////        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
////        em.setDataSource(getDataSource());
////        em.setPackagesToScan("by.tms.eshop.model");
////        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
////        em.setJpaVendorAdapter(vendorAdapter);
////        em.setJpaProperties(properties());
////        return em;
////    }
//
//
//    @Bean(name = "dataSource")
//    public DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
//        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.tomcat.driver-class-name")));
//        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
//        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
//        return dataSource;
//    }
//
//    @Bean(name = "sessionFactory")
//    public LocalSessionFactoryBean getSessionFactory(DataSource dataSource) {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPackagesToScan("by.tms.eshop");
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
//        properties.put("hibernate.show_sql", environment.getProperty("spring.jpa.properties.hibernate.show_sql"));
//        properties.put("current_session_context_class", environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
//        sessionFactory.setHibernateProperties(properties);
//        return sessionFactory;
//    }
//
//    @Bean(name = "transactionManager")
//    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
//        return new HibernateTransactionManager(sessionFactory);
//    }
//}
