import java.util.Scanner;

public class Program {
    public static void bubbleSort(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length - 1; j++) {
                if (chars[j] > chars[j + 1]) {
                    char temp = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp;
                }
            }
        }
    }

    public static void bubbleSort2(int[] nums, char[] chars) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - 1; j++) {
                if (nums[j] < nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    char temp2 = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp2;
                }
            }
        }
    }

    public static void printGraphTopTen(int[] values, char[] letters) {
        System.out.println();
        int max = values[0];
        System.out.printf("%3d\n", max);
        for (int i = 10; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (values[j] * 10 / max >= i) {
                    System.out.printf("%3c ", '#');
                }
                if (values[j] * 10 / max == i - 1 && values[j] != 0) {
                    System.out.printf("%3d ", values[j]);
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++) {
            System.out.printf("%3c ", letters[i]);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String buffer = in.nextLine();
        char[] letters = buffer.toCharArray();
        bubbleSort(letters);
        int[] occurences = new int[letters.length];
        int count = 0, i = 0;
        for (int j = 0; j < letters.length; j++) {
            char tmp = letters[j];
            while (letters[j] == tmp) {
                count++;
                j++;
                if (j == letters.length)
                    break;
            }
            occurences[i++] = count;
            if (count > 999) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
            count = 0;
            j--;
        }
        char[] newLetters = new char[i];
        int[] newOccurences = new int[i];
        for (int j = 0, k = 0; j < i; j++, k++) {
            newLetters[j] = letters[k];
            newOccurences[j] = occurences[j];
            k += occurences[j] - 1;
        }
        bubbleSort2(newOccurences, newLetters);
        printGraphTopTen(newOccurences, newLetters);
        in.close();
    }
}
