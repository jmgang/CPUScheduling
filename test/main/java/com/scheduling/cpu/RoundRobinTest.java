package main.java.com.scheduling.cpu;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.preemptive.RoundRobinCPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class RoundRobinTest {

    private CPUScheduler cpuScheduler;


    @Test
    public void testRoundRobin_sortedArrivalAndBurstWithIncreasingArrivalTimes_1() {
        ExtendedProcess[] processes = { new ExtendedProcess( 8, 0 ),
                                        new ExtendedProcess( 5, 1 ),
                                        new ExtendedProcess( 10, 2),
                                        new ExtendedProcess( 11, 3 )};
        cpuScheduler = new RoundRobinCPUScheduler( processes, BigInteger.valueOf(6) );
        cpuScheduler.computeSchedulingTimes();

        assertArrayEquals( cpuScheduler.getWaitingTimes(), new int[]{17, 5, 17, 20} );
        assertArrayEquals( cpuScheduler.getTurnAroundTimes(), new int[]{25, 10, 27, 31} );
    }

    @Test
    public void testRoundRobin_unsortedArrivalAndBurstWithIncreasingArrivalTimes_1() {
        ExtendedProcess[] processes = { new ExtendedProcess( 11, 3 ),
                new ExtendedProcess( 10, 2),
                new ExtendedProcess( 8, 0 ),
                new ExtendedProcess( 5, 1 )
                };
        cpuScheduler = new RoundRobinCPUScheduler( processes, BigInteger.valueOf(6) );
        cpuScheduler.computeSchedulingTimes();

        assertArrayEquals( cpuScheduler.getWaitingTimes(), new int[]{17, 5, 17, 20} );
        assertArrayEquals( cpuScheduler.getTurnAroundTimes(), new int[]{25, 10, 27, 31} );
    }
}
