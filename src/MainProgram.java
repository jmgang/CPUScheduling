import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.disk.base.DiskScheduler;
import main.java.com.scheduling.disk.scheduling.ScanDirection;
import main.java.com.scheduling.disk.scheduling.ScanScheduler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MainProgram {

    public static void main(String[] args) {
        CPUScheduler scheduler;
//        Process proc[] = { new Process(BigInteger.ZERO, 6, 1),
//                new Process(BigInteger.ONE, 8, 1),
//                new Process(BigInteger.TWO, 7, 2),
//                new Process(BigInteger.TEN, 3, 3)};
//        Scheduler scheduler = new ShortestRemainingTimeFirstScheduler(proc);
//        scheduler.computeSchedulingTimes();
//


//        SimpleProcess simpleProcesses[] = { new SimpleProcess(BigInteger.ZERO, 5, 0),
//                new SimpleProcess(BigInteger.ONE, 4, 1),
//                new SimpleProcess(BigInteger.TWO, 2, 2),
//                new SimpleProcess(BigInteger.TEN, 1,3)};
//        scheduler = new RoundRobinScheduler(simpleProcesses, BigInteger.valueOf(2));
//        scheduler.computeSchedulingTimes();
//
//        System.out.println( Arrays.toString(scheduler.getWaitingTimes()) );
//        System.out.println( Arrays.toString(scheduler.getTurnAroundTimes()) );

//        ExtendedProcess processes[] = { new ExtendedProcess(BigInteger.ZERO, 4, 0, 1),
//                                        new ExtendedProcess(BigInteger.ONE, 3, 0, 2),
//                                        new ExtendedProcess(BigInteger.TWO, 7, 6, 1),
//                                        new ExtendedProcess(BigInteger.valueOf(3), 4, 11, 3),
//                                        new ExtendedProcess(BigInteger.valueOf(4), 2, 12, 2)};
//        scheduler = new NonPreemptivePriorityScheduler( processes );
//        scheduler.computeSchedulingTimes();

//        SimpleProcess[] processes = { new SimpleProcess(BigInteger.ONE, 4, 0),
//                                        new SimpleProcess(BigInteger.TWO, 3, 1),
//                                        new SimpleProcess(BigInteger.valueOf(3), 1, 2),
//                                        new SimpleProcess(BigInteger.valueOf(4), 2, 3),
//                                        new SimpleProcess(BigInteger.valueOf(5), 5, 4)};
//        scheduler = new FirstComeFirstServeScheduler(processes);

//        SimpleProcess[] processes = { new SimpleProcess(BigInteger.ONE, 1, 0),
//                                new SimpleProcess(BigInteger.TWO, 3, 1),
//                                new SimpleProcess(BigInteger.valueOf(3), 2, 2),
//                                new SimpleProcess(BigInteger.valueOf(4), 4, 4)};
//        scheduler = new ShortestJobFirstCPUScheduler(processes);
//        scheduler.computeSchedulingTimes();
//
//        System.out.println( Arrays.toString(scheduler.getWaitingTimes()) );
//        System.out.println( Arrays.toString(scheduler.getTurnAroundTimes()) );

//        List<BigInteger> requests = new ArrayList<>();
//        requests.add( BigInteger.valueOf(98) );
//        requests.add( BigInteger.valueOf(183) );
//        requests.add( BigInteger.valueOf(37) );
//        requests.add( BigInteger.valueOf(122) );
//        requests.add( BigInteger.valueOf(14) );
//        requests.add( BigInteger.valueOf(124) );
//        requests.add( BigInteger.valueOf(65) );
//        requests.add( BigInteger.valueOf(67) );

        List<BigInteger> requests = new ArrayList<>();
        requests.add( BigInteger.valueOf(98) );
        requests.add( BigInteger.valueOf(137) );
        requests.add( BigInteger.valueOf(122) );
        requests.add( BigInteger.valueOf(183) );
        requests.add( BigInteger.valueOf(14) );
        requests.add( BigInteger.valueOf(133) );
        requests.add( BigInteger.valueOf(65) );
        requests.add( BigInteger.valueOf(78) );

//        DiskScheduler diskScheduler = new ShortestSeekTimeFirstScheduler( BigInteger.valueOf(53), requests, BigInteger.ONE );

        DiskScheduler diskScheduler = new ScanScheduler( BigInteger.valueOf(54), requests, BigInteger.ONE, BigInteger.valueOf(100), ScanDirection.LEFT );
        diskScheduler.computeTotalHeadMovement();



    }
}
