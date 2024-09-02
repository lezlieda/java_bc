package edu.school21.numbers;

public class NumberWorker {
    public static boolean isPrime(int number) {
        if (number < 2)
            throw new IllegalNumberException("The number " + number + " is illegal!");
        int step = 1;
        while (number % (step + 1) != 0) {
            if ((step + 1) * (step + 1) >= number) {
                return true;
            }
            step++;
        }
        return false;
    }

    public static int sumOfDigits(int number) {
        int res = 0;
        while (number > 0) {
            res += number % 10;
            number /= 10;
        }
        return res;
    }

}
