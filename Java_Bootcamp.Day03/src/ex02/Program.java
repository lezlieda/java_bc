package ex02;

import java.util.Random;

public class Program {

    public static void inputCheck(String[] args) {
        if (args.length != 2
                || !args[0].startsWith("--arraySize=")
                || args[0].substring(12).matches("^\\d+") == false
                || !args[1].startsWith("--threadsCount=")
                || args[1].substring(15).matches("^\\d+") == false) {
            System.out.println("Usage: java -jar ex02.jar --arraySize=<number> --threadsCount=<number>");
            System.exit(-1);
        }
        if (Integer.parseInt(args[0].substring(12)) > 2000000) {
            System.out.println("Maximum number of array elements is 2,000,000.");
            System.exit(-1);
        }
        if (Integer.parseInt(args[0].substring(12)) == 0) {
            System.out.println("Minimum number of array elements is 1.");
            System.exit(-1);
        }
        if (Integer.parseInt(args[1].substring(15)) > Integer.parseInt(args[0].substring(12))) {
            System.out.println("Maximum number of threads is no greater than current number of array elements.");
            System.exit(-1);
        }
        if (Integer.parseInt(args[1].substring(15)) == 0) {
            System.out.println("Minimum number of threads is 1.");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        inputCheck(args);
        int arrayElements = Integer.parseInt(args[0].substring(12));
        int numOfThreads = Integer.parseInt(args[1].substring(15));

        int[] array = new int[arrayElements];

        Random random = new Random();
        for (int i = 0; i < arrayElements; i++)
            array[i] = (random.nextInt(1000) + 1) * (int) Math.pow(-1, random.nextInt(2));

        int sumArray = 0;
        for (int i = 0; i < arrayElements; i++)
            sumArray += array[i];

        System.out.println("Sum: " + sumArray);

        int range = arrayElements / numOfThreads;
        int overallSum = 0;
        for (int i = 0; i < numOfThreads; i++) {
            int start = i * range;
            int fin = (i + 1) * range - 1;
            if (i == numOfThreads - 1)
                fin = arrayElements - 1;
            SumThread st = new SumThread(array, start, fin);
            st.start();
            try {
                st.join();
            } catch (InterruptedException e) {
            }
            overallSum += st.getLocalSum();
        }
        System.out.println("Sum by threads: " + overallSum);
    }
}
