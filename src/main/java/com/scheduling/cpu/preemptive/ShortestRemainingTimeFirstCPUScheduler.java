package main.java.com.scheduling.cpu.preemptive;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;

import java.math.BigDecimal;

public class ShortestRemainingTimeFirstCPUScheduler extends CPUScheduler {

    public ShortestRemainingTimeFirstCPUScheduler(ExtendedProcess[] extendedProcesses) {
        super( extendedProcesses.length );
        setProcesses(extendedProcesses);
        setRemainingTimes(extendedProcesses);
    }

    @Override
    public void computeWaitingTime() {

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        // Process until all processes gets
        // completed
        while (complete != this.numberOfProcesses.intValue()) {

            // Find process with minimum
            // remaining time among the
            // processes that arrives till the
            // current time`
            for (int j = 0; j < this.numberOfProcesses.intValue(); j++)
            {
                if ((simpleProcesses[j].getArrivalTime() <= t) &&
                        (remainingTimes[j] < minm) && remainingTimes[j] > 0) {
                    minm = remainingTimes[j];
                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            // Reduce remaining time by one
            remainingTimes[shortest]--;

            // Update minimum
            minm = remainingTimes[shortest];
            if (minm == 0) {
                minm = Integer.MAX_VALUE;
            }

            // If a process gets completely
            // executed
            if (remainingTimes[shortest] == 0) {

                // Increment complete
                complete++;
                check = false;

                // Find finish time of current
                // process
                finish_time = t + 1;

                // Calculate waiting time
                waitingTimes[shortest] = finish_time -
                        simpleProcesses[shortest].getBurstTime() -
                        simpleProcesses[shortest].getArrivalTime();

                if (waitingTimes[shortest] < 0) {
                    waitingTimes[shortest] = 0;
                }

            }
            // Increment time
            t++;
        }
    }

    @Override
    public void computeTurnAroundTime() {
        checkIfWaitingTimeIsComputed();
        for (int i = 0; i < this.numberOfProcesses.intValue(); i++) {
            turnAroundTimes[i] = simpleProcesses[i].getBurstTime() + waitingTimes[i];
        }

    }


}
