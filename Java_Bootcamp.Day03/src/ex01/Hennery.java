package ex01;

public class Hennery {
    private boolean piped = false;

    public synchronized void putEgg() {
        if (piped) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Egg");
        piped = true;
        notify();
    }

    public synchronized void putHen() {
        if (!piped) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hen");
        piped = false;
        notify();
    }
}
