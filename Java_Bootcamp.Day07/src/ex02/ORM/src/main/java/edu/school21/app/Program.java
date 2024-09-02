package edu.school21.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.manager.OrmManager;
import javafx.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public class Program {
    public static void main(String[] args) {
        System.out.println("helloworld");
    }


    @Contract(" -> new")
    private static @NotNull HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/");
        config.setUsername("lezlieda");
        config.setPassword("lezlieda");
        return new HikariDataSource(config);
    }

}
