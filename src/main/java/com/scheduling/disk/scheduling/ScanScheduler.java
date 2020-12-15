package main.java.com.scheduling.disk.scheduling;

import main.java.com.scheduling.disk.base.DiskScheduler;
import main.java.com.scheduling.disk.utils.MathUtils;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

public class ScanScheduler extends DiskScheduler {

    private ScanDirection direction;

    private BigInteger trackSize;

    public ScanScheduler(BigInteger currentPosition, List<BigInteger> requests, BigInteger seekRate, BigInteger trackSize, ScanDirection direction) {
        super(currentPosition, requests, seekRate);
        this.direction = direction;
        this.trackSize = trackSize;
    }

    private void addTrackBoundaries() {
        if( direction.isLeft() ) {
            requests.add(BigInteger.ZERO);
        }else {
            requests.add( trackSize.subtract(BigInteger.ONE) );
        }
    }

    @Override
    public void computeTotalHeadMovement() {
        addTrackBoundaries();
        Collections.sort( requests );
        int closestIndex = MathUtils.findClosest(requests, currentPosition);
        int pivotIndex = 0;

        if( currentPosition.compareTo( requests.get(closestIndex) ) < 0 ) {
            pivotIndex = closestIndex;
        }else {
            pivotIndex = closestIndex+1;
        }

        List<BigInteger> leftRequests = requests.subList( 0, pivotIndex );
        List<BigInteger> rightRequests = requests.subList( pivotIndex, requests.size() );
        List<BigInteger> newRequests;

        Collections.reverse(leftRequests); //descending

        if( direction.isLeft() ) {
            newRequests = leftRequests;
            newRequests.addAll(rightRequests);
        }else {
            newRequests = rightRequests;
            newRequests.addAll(leftRequests);
        }

        DiskScheduler scheduler = new FirstComeFirstServeScheduler( currentPosition, newRequests, seekRate );
        scheduler.computeTotalHeadMovement();
        totalHeadMovement = scheduler.getTotalHeadMovement();
    }
}
