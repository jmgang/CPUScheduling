package main.java.com.scheduling.cpu.process.compare;

import main.java.com.scheduling.cpu.process.ExtendedProcess;

import java.util.Comparator;

public class ExtendedProcessArrivalTimeComparator implements Comparator<ExtendedProcess> {

    @Override
    public int compare(ExtendedProcess p1, ExtendedProcess p2) {
        if (p1.getArrivalTime() < p2.getArrivalTime())
            return (-1);
        else if (p1.getArrivalTime() == p2.getArrivalTime() && p1.getBurstTime() > p2.getBurstTime())
            return (-1);
        else
            return (1);
    }
}
