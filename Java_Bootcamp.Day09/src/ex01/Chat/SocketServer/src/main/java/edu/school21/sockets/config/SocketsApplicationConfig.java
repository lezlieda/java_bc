package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import edu.school21.sockets.managers.MessagesManager;
import edu.school21.sockets.managers.UsersManager;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.MessagesServiceImpl;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("edu.school21.sockets")
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {
    @Value("${db.url}")
    private String DB_URL;
    @Value("${db.username}")
    private String DB_USERNAME;
    @Value("${db.password}")
    private String DB_PASSWORD;
    @Value("${db.driver.name}")
    private String DB_DRIVER_NAME;

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDriverClassName(DB_DRIVER_NAME);
        return dataSource;
    }

    @Bean(name = "usersRepository")
    public UsersRepository usersRepository() {
        return new UsersRepositoryImpl(dataSource());
    }

    @Bean(name = "messagesRepository")
    public MessagesRepository messagesRepository() {
        return new MessagesRepositoryImpl(dataSource());
    }

    @Bean(name = "bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "usersService")
    public UsersService usersService() {
        return new UsersServiceImpl(usersRepository(), bCryptPasswordEncoder());
    }

    @Bean(name = "messagesService")
    public MessagesService messagesService() {
        return new MessagesServiceImpl(messagesRepository());
    }

    @Bean(name = "usersManager")
    public UsersManager usersManager() {
        return UsersManager.getInstance();
    }

    @Bean(name = "messagesManager")
    public MessagesManager messagesManager() {
        return MessagesManager.getInstance();
    }
}
