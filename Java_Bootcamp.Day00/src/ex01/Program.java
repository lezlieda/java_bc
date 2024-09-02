public class Program {
    public static void main(String[] args) {
        System.out.println("Enter a number: ");
        int num = Integer.parseInt(System.console().readLine());
        if (num < 2) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        int step = 1;
        boolean isPrime = false;
        while (num % (step + 1) != 0) {
            if ((step + 1) >= Math.sqrt(num)) {
                isPrime = true;
                break;
            }
            step++;
        }
        System.out.println(isPrime + " " + step);
    }
}
