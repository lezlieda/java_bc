package ex02;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {
    private static Path currentPath;

    public static Path getCurrentPath() {
        return currentPath;
    }

    public static void getStartPath(String path) {
        Path p = Paths.get(path);
        if (Files.exists(p) == false) {
            System.out.println("Path: " + path + " does not exist");
            System.exit(1);
        }
        currentPath = p;
    }

    public static void fileManager(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "ls":
                if (parts.length != 1) {
                    System.out.println("Usage: ls");
                    break;
                } else
                    listFiles();
                break;
            case "cd":
                if (parts.length != 2) {
                    System.out.println("Usage: cd <path>");
                    break;
                } else
                    changeDirectory(parts[1]);
                break;
            case "mv":
                if (parts.length != 3) {
                    System.out.println("Usage: mv <source> <destination>");
                    break;
                } else
                    moveFile(parts[1], parts[2]);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }

    private static void listFiles() {
        try {
            for (Path p : Files.newDirectoryStream(currentPath)) {
                String output = p.getFileName().toString();
                long size = 0;
                if (Files.isDirectory(p))
                    size = folderSize(p.toFile());
                else
                    size = Files.size(p);
                output += " " + size / 1024 + " KB";
                System.out.println(output);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }

    private static void changeDirectory(String path) {
        Path p = currentPath.resolve(path);
        if (Files.exists(p) == false) {
            System.out.println("Path: " + p.toString() + " does not exist");
            return;
        }
        if (Files.isDirectory(p) == false) {
            System.out.println("Path: " + p.toString() + " is not a directory");
            return;
        }
        currentPath = p;
        pathShortener();
        System.out.println(currentPath.toString());
    }

    private static void pathShortener() {
        String path = currentPath.toString();
        String[] parts = path.split("/");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("..")) {
                parts[i] = "";
                parts[i - 1] = "";
            }
        }
        path = "/";
        for (String part : parts) {
            if (part.equals("") == false)
                path += part + "/";
        }
        currentPath = Paths.get(path);
    }

    private static void moveFile(String source, String destination) {
        Path s = currentPath.resolve(source);
        Path d = currentPath.resolve(destination);
        if (Files.exists(s) == false) {
            System.out.println("Source: " + s.toString() + " does not exist");
            return;
        }
        if (d != null && Files.isDirectory(d)) {
            d = d.resolve(s.getFileName());
        }
        try {
            Files.move(s, d, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
