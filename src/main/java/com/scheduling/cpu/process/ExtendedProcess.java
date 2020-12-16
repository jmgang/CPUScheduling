package main.java.com.scheduling.cpu.process;

import java.math.BigInteger;
import java.util.Objects;

public class ExtendedProcess extends SimpleProcess {

    private int tempBurstTime;

    private int responseTime;

    private int priority;

    private int outTime;

    private int inTime;

    private int waitingTime;

    private int turnAroundTime;

    private int remainingTime;

    private int completionTime;


    public ExtendedProcess() {
        super();
    }

    public ExtendedProcess(int burstTime, int arrivalTime) {
        super(burstTime, arrivalTime);
        setRemainingTime(burstTime);
    }

    public ExtendedProcess(BigInteger processId, int burstTime, int arrivalTime) {
        super(processId, burstTime, arrivalTime);
    }

    public ExtendedProcess( int burstTime, int arrivalTime, int priority ) {
        this( burstTime, arrivalTime );
        this.priority = priority;
        setRemainingTime(burstTime);
    }

    public ExtendedProcess(BigInteger processId, int burstTime, int arrivalTime, int priority) {
        this(processId, burstTime, arrivalTime);
        this.priority = priority;
    }

    public ExtendedProcess(BigInteger processId, int burstTime, int arrivalTime, int tempBurstTime, int responseTime, int priority, int outTime, int inTime) {
        super(processId, burstTime, arrivalTime);
        this.tempBurstTime = tempBurstTime;
        this.responseTime = responseTime;
        this.priority = priority;
        this.outTime = outTime;
        this.inTime = inTime;
    }

    public int getTempBurstTime() {
        return tempBurstTime;
    }

    public void setTempBurstTime(int tempBurstTime) {
        this.tempBurstTime = tempBurstTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getOutTime() {
        return outTime;
    }

    public void setOutTime(int outTime) {
        this.outTime = outTime;
    }

    public int getInTime() {
        return inTime;
    }

    public void setInTime(int inTime) {
        this.inTime = inTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void execute() {
        this.remainingTime--;
    }

    @Override
    public String toString() {
        return "(pid=" + super.getProcessId() + ", at=" + super.getArrivalTime() + ", bt=" + super.getBurstTime() + ",p=" + this.getPriority() +
                ",rem=" + this.getRemainingTime() + ")"  ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedProcess process = (ExtendedProcess) o;
        return this.getProcessId().compareTo(process.getProcessId()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority);
    }
}
