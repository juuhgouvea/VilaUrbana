package sample.Model;

public class Timer extends Thread {
    private int time = 0;
    private static Timer instance = null;

    private Timer() {}

    public static Timer getInstance() {
        if(instance == null) {
            instance = new Timer();
        }

        return instance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.setTime(getTime() + 1);
                Thread.sleep(1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
