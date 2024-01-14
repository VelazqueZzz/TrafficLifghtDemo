public class TrafficLightSimulator implements Runnable {
    private Thread thrd;
    private TrafficLightColor tic;
    boolean stop = false;
    boolean changed = false;


    TrafficLightSimulator(TrafficLightColor init) {
        tic = init;
        thrd = new Thread(this);
        thrd.start();
    }

    TrafficLightSimulator() {
        tic = TrafficLightColor.RED;
        thrd = new Thread(this);
        thrd.start();

    }

    public void run() {
        while (!stop) {

            try {
                switch (tic) {
                    case GREEN:
                        Thread.sleep(10000); // Зеленый на 10 секунд
                        break;
                    case YELLOW:
                        Thread.sleep(2000); // Желтый на 2 секунды
                        break;
                    case RED:
                        Thread.sleep(12000); // Красный на 12 секунд
                        break;
                }
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
            changeColor();
        }
    }

    synchronized void changeColor() {

        switch (tic) {
            case RED:
                tic = TrafficLightColor.GREEN;
                break;
            case YELLOW:
                tic = TrafficLightColor.RED;
                break;
            case GREEN:
                tic = TrafficLightColor.YELLOW;
        }

        changed = true;
        notify(); // уведомить о переключении цвета светофора
    }

    synchronized void waitForChange() {
        try {
            while (!changed)
                wait(); // ожидать переключения цвета светофора
            changed = false;
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
    }

    TrafficLightColor getColor() {
        return tic;
    }

    // Прекращение имитации светофора.
    void cancel() {
        stop = true;
    }
}
