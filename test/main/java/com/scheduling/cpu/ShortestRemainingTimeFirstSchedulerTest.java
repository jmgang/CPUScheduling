package main.java.com.scheduling.cpu;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.nonpreemptive.ShortestJobFirstCPUScheduler;
import main.java.com.scheduling.cpu.preemptive.ShortestRemainingTimeFirstCPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ShortestRemainingTimeFirstSchedulerTest {
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
        cpuScheduler = new ShortestRemainingTimeFirstCPUScheduler( processes );
        cpuScheduler.computeSchedulingTimes();

        System.out.println(Arrays.toString( cpuScheduler.getWaitingTimes() ));
        System.out.println(Arrays.toString( cpuScheduler.getTurnAroundTimes() ));

        assertArrayEquals( cpuScheduler.getWaitingTimes(), new int[]{12, 6, 0, 15, 0} );
        assertArrayEquals( cpuScheduler.getTurnAroundTimes(), new int[]{20, 12, 3, 24, 3} );
    }
}
