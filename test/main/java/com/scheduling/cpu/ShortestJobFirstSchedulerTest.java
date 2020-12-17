package main.java.com.scheduling.cpu;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.nonpreemptive.ShortestJobFirstCPUScheduler;
import main.java.com.scheduling.cpu.preemptive.RoundRobinCPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

public class ShortestJobFirstSchedulerTest {

    private CPUScheduler cpuScheduler;

    @Test
    public void testSJF_valid_1() {
        ExtendedProcess[] processes = {
                new ExtendedProcess( 8, 0),
                new ExtendedProcess( 6, 2),
                new ExtendedProcess( 3,3),
                new ExtendedProcess( 9, 5 ),
                new ExtendedProcess( 3, 6 )
        };
        cpuScheduler = new ShortestJobFirstCPUScheduler( processes );
        cpuScheduler.computeSchedulingTimes();

        System.out.println(Arrays.toString( cpuScheduler.getWaitingTimes() ));
        System.out.println(Arrays.toString( cpuScheduler.getTurnAroundTimes() ));
    }
}
