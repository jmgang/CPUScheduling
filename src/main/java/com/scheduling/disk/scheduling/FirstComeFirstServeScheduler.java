package main.java.com.scheduling.disk.scheduling;

import main.java.com.scheduling.disk.base.DiskScheduler;
import main.java.com.scheduling.disk.utils.MathUtils;

import java.math.BigInteger;
import java.util.List;

public class FirstComeFirstServeScheduler extends DiskScheduler {

    public FirstComeFirstServeScheduler(BigInteger currentPosition, List<BigInteger> requests, BigInteger seekRate) {
        super(currentPosition, requests, seekRate);
    }

    @Override
    public void computeTotalHeadMovement() {
        totalHeadMovement = MathUtils.getPositiveDifference( requests.get(0), currentPosition );
       for( int i = 1; i < requests.size(); i++ ) {
            totalHeadMovement = totalHeadMovement.add( MathUtils.getPositiveDifference( requests.get(i), requests.get(i-1) ) );
       }
    }


}
