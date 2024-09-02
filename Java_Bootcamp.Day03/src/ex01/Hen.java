package ex01;

public class Hen implements Runnable {
    private int count;
    Hennery hennery;

    public Hen(int count, Hennery hennery) {
        this.count = count;
        this.hennery = hennery;
    }

    @Override
    public void run() {
        while (count-- > 0)
            hennery.putHen();
    }
}
