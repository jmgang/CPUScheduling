package main.java.com.scheduling.disk.base;

import java.math.BigInteger;
import java.util.List;

public abstract class DiskScheduler implements DiskSchedulingManager {

    protected BigInteger totalHeadMovement;

    protected  BigInteger currentPosition;

    protected List<BigInteger> requests;

    protected BigInteger seekRate;

    public DiskScheduler( BigInteger currentPosition, List<BigInteger> requests, BigInteger seekRate ) {
        this.currentPosition = currentPosition;
        this.requests = requests;
        this.seekRate = seekRate;
    }

    public BigInteger getSeekTime() {
        return this.totalHeadMovement.multiply(this.seekRate);
    }

    public BigInteger getTotalHeadMovement() {
        return totalHeadMovement;
    }

    public void setTotalHeadMovement(BigInteger totalHeadMovement) {
        this.totalHeadMovement = totalHeadMovement;
    }

    public List<BigInteger> getRequests() {
        return requests;
    }

    public void setRequests(List<BigInteger> requests) {
        this.requests = requests;
    }

    public BigInteger getSeekRate() {
        return seekRate;
    }

    public void setSeekRate(BigInteger seekRate) {
        this.seekRate = seekRate;
    }

}
