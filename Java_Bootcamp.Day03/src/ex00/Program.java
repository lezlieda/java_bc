package ex00;

public class Program {
    public static void main(String[] args) {

        if (args.length != 1 || !args[0].startsWith("--count=")
                || args[0].substring(8).matches("^\\d+") == false) {
            System.out.println("Usage: java -jar ex00.jar --count=<number>");
            System.exit(-1);
        }

        int n = Integer.parseInt(args[0].substring(8));
        Thread egg = new MyThread(n, "Egg");
        Thread hen = new MyThread(n, "Hen");

        egg.start();
        hen.start();
        try {
            egg.join();
            hen.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (n > 0) {
            System.out.println("Human");
            n--;
        }
    }
}
