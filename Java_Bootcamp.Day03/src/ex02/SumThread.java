package ex02;

public class SumThread extends Thread {
    private int[] array;
    private int firstIndex;
    private int lastIndex;
    private int localSum = 0;

    public SumThread(int[] array, int firstIndex, int lastIndex) {
        this.array = array;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public int getLocalSum() {
        return localSum;
    }

    @Override
    public void run() {
        for (int i = firstIndex; i <= lastIndex; i++)
            localSum += array[i];
        System.out.println("Thread " + (1 + Integer.parseInt(Thread.currentThread().getName().substring(7))) +
                ": from " + firstIndex + " to " + lastIndex + " sum is " + localSum);
    }

}
