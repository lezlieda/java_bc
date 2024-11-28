package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

public class Program {
    public static void main(String[] args) {
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(getDataSource());
        usersRepository.findAll(1, 2).forEach(System.out::println);

    }

    private static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/");
        config.setUsername("postgres");
        config.setPassword("qwe");
        return new HikariDataSource(config);
    }
}
