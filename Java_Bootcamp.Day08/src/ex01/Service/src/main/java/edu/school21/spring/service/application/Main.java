package edu.school21.spring.service.application;

import edu.school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        System.out.println("UsersRepositoryJdbcImpl (uses standard Statements mechanisms):");
        UsersRepositoryJdbcImpl usersRepositoryJdbc = context.getBean("usersRepositoryJdbc", UsersRepositoryJdbcImpl.class);
        System.out.println(usersRepositoryJdbc.findAll());
        System.out.println("UsersRepositoryJdbcTemplateImpl (based on JdbcTemplate/NamedParameterJdbcTemplate):");
        UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepositoryJdbcTemplateImpl.class);
        System.out.println(usersRepositoryJdbcTemplate.findAll());
    }
}
