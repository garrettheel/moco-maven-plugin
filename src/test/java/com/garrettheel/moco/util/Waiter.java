package com.garrettheel.moco.util;

public class Waiter {
    private int interval = 500;
    private int max = 10000;

    public Waiter() {

    }

    public Waiter(int interval, int max) {
        this.interval = interval;
        this.max = max;
    }

    public void until(Condition condition) {
        until(condition, null);
    }

    public void until(Condition condition, TimeOutCallBack back) {
        boolean flag = true;
        int count = 0;
        do {
            if (condition.check()) {
                flag = false;
            } else {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count += interval;
        } while (flag && count < max);
        if (flag) {
            if (back == null) {
                throw new timeOutException();
            } else {
                back.execute();
            }
        }
    }

    public interface Condition {
        boolean check();
    }

    public interface TimeOutCallBack {
        void execute();
    }

    private class timeOutException extends RuntimeException {
    }
}

