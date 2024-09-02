package ex00;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean append = false;
        while (input.equals("42") == false) {
            SignatureScanner.filesAnalysis(input, append);
            input = scanner.nextLine();
            append = true;
        }
        scanner.close();
    }
}
