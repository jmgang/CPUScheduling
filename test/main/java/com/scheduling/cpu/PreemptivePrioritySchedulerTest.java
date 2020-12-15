package main.java.com.scheduling.cpu;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.preemptive.PreemptivePriorityCPUScheduler;
import main.java.com.scheduling.cpu.preemptive.RoundRobinCPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PreemptivePrioritySchedulerTest {

    private CPUScheduler cpuScheduler;

    @Test
    public void testPreempPrio_valid_1() {
        ExtendedProcess[] processes = {
                new ExtendedProcess( 1, 0, 2),
                new ExtendedProcess( 7, 1, 6 ),
                new ExtendedProcess( 3,2, 3),
                new ExtendedProcess( 6, 3, 5 ),
                new ExtendedProcess( 5, 4, 4 ),
                new ExtendedProcess( 15, 5, 10 ),
                new ExtendedProcess( 8, 6, 9 )
        };
        cpuScheduler = new PreemptivePriorityCPUScheduler( processes );
        cpuScheduler.computeSchedulingTimes();

        System.out.println(Arrays.toString( cpuScheduler.getWaitingTimes() ));
        System.out.println(Arrays.toString( cpuScheduler.getTurnAroundTimes() ));

        assertArrayEquals( cpuScheduler.getWaitingTimes(), new int[]{0, 14, 0, 7, 1, 25, 16} );
        assertArrayEquals( cpuScheduler.getTurnAroundTimes(), new int[]{1, 21, 3, 13, 6, 40, 24} );
    }
}
