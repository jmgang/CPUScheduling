package main.java.com.scheduling.cpu.preemptive;

import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.cpu.process.compare.ExtendedProcessArrivalTimeComparator;
import main.java.com.scheduling.cpu.base.CPUScheduler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class RoundRobinCPUScheduler extends CPUScheduler {

    private ExtendedProcess[] extendedProcesses;

    private BigInteger quantum;

    private int[] completionTime;

    public RoundRobinCPUScheduler(ExtendedProcess[] extendedProcesses, BigInteger quantum ) {
        super( extendedProcesses.length );
        this.quantum = quantum;
        this.completionTime = new int[numberOfProcesses.intValue()];
        this.extendedProcesses = extendedProcesses;
    }

    @Override
    protected void sortByArrivalTime() {
        Arrays.sort( extendedProcesses, new ExtendedProcessArrivalTimeComparator());
    }

    @Override
    public void computeWaitingTime() {
        sortByArrivalTime();
        setRemainingTimes(extendedProcesses);

        BigInteger currentTime = BigInteger.ZERO;
        int arrival=0;
        // processing until the value of element of rem_time array is 0
        while(true){
//            ;
            boolean done = true;
            for(int i=0; i < numberOfProcesses.intValue() ;i++){
                if(remainingTimes[i]>0){
                    done =false;
                    if(remainingTimes[i]>quantum.intValue() && extendedProcesses[i].getArrivalTime() <= arrival){
                        currentTime = currentTime.add(quantum);
                        remainingTimes[i] -= quantum.intValue();
                        arrival++;
                    }
                    else{
                        if(extendedProcesses[i].getArrivalTime() <= arrival){
                            arrival++;
                            currentTime = currentTime.add(BigInteger.valueOf(remainingTimes[i]));
                            remainingTimes[i] = 0;
                            completionTime[i] = currentTime.intValue();
                        }
                    }
                }
            }

            if(done)
            {
                break;
            }
        }
    }

    @Override
    public void computeTurnAroundTime() {
        checkIfWaitingTimeIsComputed();
        for(int i=0; i < numberOfProcesses.intValue() ;i++){
            turnAroundTimes[i]= completionTime[i] - extendedProcesses[i].getArrivalTime();
            waitingTimes[i] = turnAroundTimes[i] - extendedProcesses[i].getBurstTime();
        }
    }

}
