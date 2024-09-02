package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || args[0].startsWith("--current-folder=") == false) {
            System.out.println("Usage: java ex02.Program --current-folder=<path>");
            System.exit(1);
        }
        FileManager.getStartPath(args[0].substring(17));
        System.out.println(FileManager.getCurrentPath().toString());
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input.equals("exit") == false) {
            FileManager.fileManager(input);
            input = scanner.nextLine();
        }
        scanner.close();

    }
}
