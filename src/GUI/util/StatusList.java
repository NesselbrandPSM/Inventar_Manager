package GUI.util;

import Main.utility.StringDoubleTupel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class StatusList extends Thread {

    JLabel out;

    private ArrayList<StringDoubleTupel> statusList;
    private Semaphore semaphore;

    public StatusList(JLabel jLabel) {
        out = jLabel;
        statusList = new ArrayList<>();
        semaphore = new Semaphore(2, false);
    }

    public void add(String message, double showTime) {
        try {
            semaphore.acquire();
            statusList.add(new StringDoubleTupel(message, showTime));
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private StringDoubleTupel remove() {
        try {
            semaphore.acquire();
            StringDoubleTupel ret = statusList.remove(0);
            semaphore.release();
            return ret;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                if (statusList.size() > 0) {
                    StringDoubleTupel rel = remove();
                    semaphore.release();
                    out.setText(rel.string);
                    Thread.sleep((long) (rel.x * 1000L));
                    out.setText("");
                } else {
                    semaphore.release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}