package by.tms.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "by.tms.eshop.repository")
//@PropertySource("classpath:application.properties")
//@RequiredArgsConstructor
public class EshopSpringBootTestApplication {
//    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(EshopSpringBootTestApplication.class, args);
    }

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



}