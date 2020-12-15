package main.java.com.scheduling.disk;


import main.java.com.scheduling.disk.base.DiskScheduler;
import main.java.com.scheduling.disk.scheduling.ScanDirection;
import main.java.com.scheduling.disk.scheduling.ScanScheduler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScanSchedulerTest {

    public void setup() {

    }

    @Test
    public void testTotalHeadMovement_leftDirection_237() {
        List<BigInteger> requests = toBigInt( Arrays.asList( 98, 137, 122, 183, 14, 133, 65, 78 ) );
        DiskScheduler diskScheduler = new ScanScheduler(BigInteger.valueOf(54), requests, BigInteger.ONE, BigInteger.valueOf(200), ScanDirection.LEFT);
        diskScheduler.computeTotalHeadMovement();
        assertEquals( diskScheduler.getTotalHeadMovement(), BigInteger.valueOf(237) );
    }

    @Test
    public void testTotalHeadMovement_rightDirection_332() {
        List<BigInteger> requests = toBigInt(Arrays.asList( 82, 170, 43, 140, 24, 16, 190 ));
        DiskScheduler diskScheduler = new ScanScheduler(BigInteger.valueOf(50), requests, BigInteger.ONE, BigInteger.valueOf(200), ScanDirection.RIGHT);
        diskScheduler.computeTotalHeadMovement();
        assertEquals( diskScheduler.getTotalHeadMovement(), BigInteger.valueOf(332) );
    }

    private List<BigInteger> toBigInt( List<Integer> list ) {
        List<BigInteger> newList = new ArrayList<>();
        list.forEach( integer -> newList.add( BigInteger.valueOf(integer) ) );
        return newList;
    }

    public void tearDown() {

    }
}
