package main.java.com.scheduling.cpu.process;

import java.math.BigInteger;

public class SimpleProcess extends AbstractProcess {


    private int burstTime;

    private int arrivalTime;

    public SimpleProcess(){
        super();
    }

    public SimpleProcess(int burstTime, int arrivalTime) {
        super();
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public SimpleProcess(BigInteger processId, int burstTime) {
        this( processId, burstTime, 0 );
    }

    public SimpleProcess(BigInteger processId, int burstTime, int arrivalTime) {
        this();
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "(pid=" + super.getProcessId() + ", at=" + this.arrivalTime + ", bt=" + this.burstTime +  ")"  ;
    }
}
