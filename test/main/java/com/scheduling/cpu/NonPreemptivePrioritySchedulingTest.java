package main.java.com.scheduling.cpu;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.nonpreemptive.NonPreemptivePriorityCPUScheduler;
import main.java.com.scheduling.cpu.preemptive.PreemptivePriorityCPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class NonPreemptivePrioritySchedulingTest {

    private CPUScheduler cpuScheduler;

    @Test
    public void testNonPreemptivePriority_valid_1() {
        ExtendedProcess[] processes = {
                new ExtendedProcess( 1, 0, 2),
                new ExtendedProcess( 7, 1, 6 ),
                new ExtendedProcess( 3, 2, 3),
                new ExtendedProcess( 6, 3, 5 ),
                new ExtendedProcess( 5, 4, 4 ),
                new ExtendedProcess( 15, 5, 10),
                new ExtendedProcess( 8, 15, 9 )};
        cpuScheduler = new NonPreemptivePriorityCPUScheduler( processes );
        cpuScheduler.computeSchedulingTimes();

        System.out.println(Arrays.toString( cpuScheduler.getWaitingTimes() ));
        System.out.println(Arrays.toString( cpuScheduler.getTurnAroundTimes() ));

        assertArrayEquals( cpuScheduler.getWaitingTimes(), new int[]{0, 14, 0, 7, 1, 25, 16} );
        assertArrayEquals( cpuScheduler.getTurnAroundTimes(), new int[]{1, 21, 3, 13, 6, 40, 24} );
    }

    @Test
    public void testNonPreemptivePriority_valid_2() {
        ExtendedProcess[] processes = {
                new ExtendedProcess( 3, 0, 2),
                new ExtendedProcess( 5, 2, 6 ),
                new ExtendedProcess( 4, 1, 3),
                new ExtendedProcess( 2, 4, 5 ),
                new ExtendedProcess( 9, 6, 7 ),
                new ExtendedProcess( 4, 5, 4 ),
                new ExtendedProcess( 10, 7, 10 )};
        cpuScheduler = new NonPreemptivePriorityCPUScheduler( processes );
        cpuScheduler.computeSchedulingTimes();

        System.out.println(Arrays.toString( cpuScheduler.getWaitingTimes() ));
        System.out.println(Arrays.toString( cpuScheduler.getTurnAroundTimes() ));

        assertArrayEquals( cpuScheduler.getWaitingTimes(), new int[]{0, 11, 2, 7, 12, 2, 20} );
        assertArrayEquals( cpuScheduler.getTurnAroundTimes(), new int[]{3, 16, 6, 9, 21, 6, 30} );
    }
}
