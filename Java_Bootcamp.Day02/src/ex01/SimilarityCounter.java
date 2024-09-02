package ex01;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class SimilarityCounter {
    private static String output = "dictionary.txt";

    public static Set<String> createDictionarySet(String path1, String path2) {
        Set<String> words = new HashSet<String>();
        try (FileReader fileReader1 = new FileReader(path1); FileReader fileReader2 = new FileReader(path2)) {
            Scanner scanner1 = new Scanner(fileReader1);
            Scanner scanner2 = new Scanner(fileReader2);
            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                String[] parts = line.split("[\\p{Punct}\\s]+");
                for (String word : parts) {
                    words.add(word);
                }
            }
            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                String[] parts = line.split("[\\p{Punct}\\s]+");
                for (String word : parts) {
                    words.add(word);
                }
            }
            scanner1.close();
            scanner2.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return words;
    }

    public static Map<String, Integer> countWords(String path, Set<String> words) {
        Map<String, Integer> wordCount = new HashMap<String, Integer>();
        try (FileReader fileReader = new FileReader(path)) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("[\\p{Punct}\\s]+");
                for (String word : parts) {
                    if (words.contains(word)) {
                        if (wordCount.containsKey(word)) {
                            wordCount.put(word, wordCount.get(word) + 1);
                        } else {
                            wordCount.put(word, 1);
                        }
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return wordCount;
    }

    public static double Returns(String path1, String path2) {
        Set<String> words = createDictionarySet(path1, path2);
        Map<String, Integer> wordCount1 = countWords(path1, words);
        Map<String, Integer> wordCount2 = countWords(path2, words);
        double dotProduct = 0;
        double magnitude1 = 0;
        double magnitude2 = 0;
        boolean append = false;
        for (String word : words) {
            if (word.equals("")) {
                continue;
            }
            writeFile(word + "\n", append);
            int count1 = wordCount1.getOrDefault(word, 0);
            int count2 = wordCount2.getOrDefault(word, 0);
            dotProduct += count1 * count2;
            magnitude1 += count1 * count1;
            magnitude2 += count2 * count2;
            append = true;
        }
        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);
        return dotProduct / (magnitude1 * magnitude2);
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

}
