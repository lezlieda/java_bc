package ex01;

public class Egg implements Runnable {
    private int count;
    private Hennery hennery;

    public Egg(int count, Hennery hennery) {
        this.count = count;
        this.hennery = hennery;
    }

    @Override
    public void run() {
        while (count-- > 0)
            hennery.putEgg();
    }
}
