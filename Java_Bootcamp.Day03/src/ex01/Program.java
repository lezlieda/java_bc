package ex01;

public class Program {
    public static void main(String[] args) {

        if (args.length != 1 || !args[0].startsWith("--count=")
                || args[0].substring(8).matches("^\\d+") == false) {
            System.out.println("Usage: java -jar ex01.jar --count=<number>");
            System.exit(-1);
        }

        Hennery hennery = new Hennery();
        Egg egg = new Egg(Integer.parseInt(args[0].substring(8)), hennery);
        Hen hen = new Hen(Integer.parseInt(args[0].substring(8)), hennery);

        new Thread(egg).start();
        new Thread(hen).start();

    }
}
