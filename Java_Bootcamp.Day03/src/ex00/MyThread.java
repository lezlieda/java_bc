package ex00;

public class MyThread extends Thread {
    private int count;
    private String threadName;

    public MyThread(int n, String name) {
        this.count = n;
        this.threadName = name;
    }

    @Override
    public void run() {
        while (count-- > 0) {
            System.out.println(threadName);
        }
    }
}
