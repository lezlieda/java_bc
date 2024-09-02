package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ex01.Program <inputA> <inputB>");
            return;
        }
        try {
            double similarity = SimilarityCounter.Returns(args[0], args[1]);
            System.out.printf("Similarity = %.2f\n", similarity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

    }
}
