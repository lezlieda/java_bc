package ex00;

import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class SignatureScanner {
    private static final String signatures = "signatures.txt";
    private static final String output = "result.txt";
    private static final Map<String, String> map = buildMap();

    public static Map<String, String> buildMap() {
        Map<String, String> res = new HashMap<String, String>();
        try (FileReader fileReader = new FileReader(signatures)) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                res.put(parts[1], parts[0]);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public static String getByteFromFile(String filename) {
        byte[] bytes = new byte[0];
        String hex = "";
        try {
            bytes = Files.readAllBytes(Paths.get(filename));
            for (byte b : bytes) {
                hex += String.format("%02X ", b);
            }
        } catch (Exception e) {
            System.out.println("File: " + e.getMessage() + " not found");
        }
        return hex;
    }

    public static void writeFile(String contenet, boolean append) {
        try {
            File file = new File(output);
            file.createNewFile();
            FileWriter writer = new FileWriter(file, append);
            writer.write(contenet);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void filesAnalysis(String path, boolean append) {
        String hex = getByteFromFile(path);
        boolean found = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (hex.contains(key)) {
                writeFile(entry.getValue() + "\n", append);
                found = true;
                break;
            }
        }
        if (hex.length() > 0) {
            if (found == false) {
                writeFile("UNDEFINED\n", append);
            }
            System.out.println("PROCESSED");
        }
    }

}
