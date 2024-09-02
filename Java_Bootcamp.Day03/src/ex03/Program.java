package ex03;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void inputCheck(String[] args) {
        if (args.length != 1
                || !args[0].startsWith("--threadsCount=")
                || args[0].substring(15).matches("^\\d+") == false) {
            System.out.println("Usage: java -jar ex02.jar --threadsCount=<number>");
            System.exit(-1);
        }
        if (Integer.parseInt(args[0].substring(15)) == 0) {
            System.out.println("Minimum number of threads is 1.");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        inputCheck(args);
        int threadsCount = Integer.parseInt(args[0].substring(15));
        List<Downloader> threads = new ArrayList<>(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            threads.add(new Downloader(threadsCount, i));
            threads.get(i).start();
        }
        for (int i = 0; i < threadsCount; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
