public class Program {
    public static int sumOfDigits(int num) {
        int res = 0;
        while (num > 0) {
            res += num % 10;
            num /= 10;
        }
        return res;
    }

    public static boolean isPrime(int num) {
        int step = 1;
        while (num % (step + 1) != 0) {
            if ((step + 1) >= Math.sqrt(num)) {
                return true;
            }
            step++;
        }
        return false;
    }

    public static void main(String[] args) {
        int num = Integer.parseInt(System.console().readLine());
        int res = 0;
        while (num != 42) {
            if (num > 1) {
                int sum = sumOfDigits(num);
                if (isPrime(sum))
                    res++;
            }
            num = Integer.parseInt(System.console().readLine());
        }
        System.out.println("Count of coffee-request - " + res);
    }
}
