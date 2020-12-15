package main.java.com.scheduling.cpu.process.compare;

import main.java.com.scheduling.cpu.process.SimpleProcess;

import java.util.Comparator;

public class SimpleProcessArrivalTimeComparator implements Comparator<SimpleProcess> {
    @Override
    public int compare(SimpleProcess p1, SimpleProcess p2) {
        if (p1.getArrivalTime() < p2.getArrivalTime())
            return (-1);
        else if (p1.getArrivalTime() == p2.getArrivalTime() && p1.getBurstTime() > p2.getBurstTime())
            return (-1);
        else
            return (1);
    }
}
