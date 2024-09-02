import java.util.Scanner;
import java.util.regex.Pattern;

public class Program {
    public static int getLowestNum(int num) {
        int res = 9;
        if (countDigits(num) != 5) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        while (num > 0) {
            if (num % 10 < res)
                res = num % 10;
            num /= 10;
        }
        if (res == 0) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        return res;
    }

    public static int countDigits(long num) {
        int res = 0;
        while (num > 0) {
            res++;
            num /= 10;
        }
        return res;
    }

    public static void printGraph(long num) {
        int digits = countDigits(num);
        for (int i = 1; i <= digits; i++) {
            System.out.print("Week " + i + " ");
            for (int j = 1; j <= num % 10; j++) {
                System.out.print("=");
            }
            System.out.println(">");
            num /= 10;
        }
    }

    public static void main(String[] args) {
        int week = 1;
        long minGrades = 0;
        Scanner in = new Scanner(System.in);
        String buffer = in.nextLine();
        while (week <= 18 && buffer.equals("42") == false) {
            if (buffer.equals("Week " + week) == false) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            } else {
                buffer = in.nextLine();
                Pattern pattern = Pattern.compile("^\\s*\\d{1}\\s+\\d{1}\\s+\\d{1}\\s+\\d{1}\\s+\\d{1}\\s*$");
                if (pattern.matcher(buffer).matches() == false) {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }
                minGrades += getLowestNum(Integer.parseInt(buffer.replaceAll("\\s+", ""))) * Math.pow(10, week - 1);
            }
            buffer = in.nextLine();
            week++;
        }
        in.close();
        printGraph(minGrades);
    }
}
