package edu.school21.spring.service.config;

import edu.school21.spring.service.repositories.UsersRepository;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@ComponentScan("edu.school21.spring.service")
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    @Value("${db.url}")
    private String DB_URL;
    @Value("${db.username}")
    private String DB_USERNAME;
    @Value("${db.password}")
    private String DB_PASSWORD;
    @Value("${db.driver.name}")
    private String DB_DRIVER_NAME;

    @Bean(name = "driverManagerDataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDriverClassName(DB_DRIVER_NAME);
        return dataSource;
    }

    @Bean
    public UsersRepository usersRepositoryJdbc() {
        return new UsersRepositoryJdbcImpl(dataSource());
    }

    @Bean
    public UsersRepository usersRepositoryJdbcTemplate() {
        return new UsersRepositoryJdbcTemplateImpl(dataSource());
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("data.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }


}
