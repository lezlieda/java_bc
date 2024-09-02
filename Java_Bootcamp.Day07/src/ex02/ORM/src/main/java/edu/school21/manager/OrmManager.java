package edu.school21.manager;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class OrmManager {
    private final DataSource dataSource;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static Map<String, Class<?>> getClasses() throws IOException, ClassNotFoundException {
        Path path;
        Map<String, Class<?>> ret = new HashMap<>();
        path = Paths.get("target/classes/edu/school21/model");
        for (Path p : Files.newDirectoryStream(path)) {
            String fileName = p.getFileName().toString();
            if (fileName.endsWith(".class")) {
                ret.put(fileName.replaceAll("\\.class", ""), Class.forName(fileName));
            }
        }
        return ret;
    }


}
