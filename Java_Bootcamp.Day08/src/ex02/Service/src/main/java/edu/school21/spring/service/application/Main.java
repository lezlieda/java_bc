package edu.school21.spring.service.application;

import edu.school21.spring.service.config.ApplicationConfig;
import edu.school21.spring.service.repositories.UsersRepository;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersRepository usersRepositoryJdbc = context.getBean("usersRepositoryJdbc", UsersRepositoryJdbcImpl.class);
        System.out.println("UsersRepositoryJdbcImpl (uses standard Statements mechanisms):");
        System.out.println(usersRepositoryJdbc.findAll());
        System.out.println("UsersRepositoryJdbcImpl (uses JDBC Template mechanisms):");
        UsersRepository usersRepositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepositoryJdbcTemplateImpl.class);
        System.out.println(usersRepositoryJdbcTemplate.findAll());


    }
}
