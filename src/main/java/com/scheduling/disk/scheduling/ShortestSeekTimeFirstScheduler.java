package main.java.com.scheduling.disk.scheduling;

import main.java.com.scheduling.disk.base.DiskScheduler;
import main.java.com.scheduling.disk.utils.MathUtils;

import java.math.BigInteger;
import java.util.List;

public class ShortestSeekTimeFirstScheduler extends DiskScheduler {


    public ShortestSeekTimeFirstScheduler(BigInteger currentPosition, List<BigInteger> requests, BigInteger seekRate) {
        super(currentPosition, requests, seekRate);
    }

    @Override
    public void computeTotalHeadMovement() {
        List<BigInteger> queue = requests;
        int lastClosestIndex = MathUtils.findClosest( queue, currentPosition );
        BigInteger currentClosest = queue.get( lastClosestIndex );
        BigInteger currentPos = currentClosest;
        queue.remove( lastClosestIndex );

        totalHeadMovement = MathUtils.getPositiveDifference( currentPosition, currentClosest );

        while( !queue.isEmpty() ) {
            lastClosestIndex = MathUtils.findClosest( queue, currentClosest );
            currentClosest = queue.get( lastClosestIndex );
            totalHeadMovement = totalHeadMovement.add( MathUtils.getPositiveDifference( currentClosest, currentPos ) );
            currentPos = currentClosest;
            queue.remove( lastClosestIndex );
        }
    }
}
