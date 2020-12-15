package main.java.com.scheduling.main;

public enum SchedulerType {

    PREEMPTIVE, NON_PREEMPTIVE;

    public boolean isPreemptive() {
        return this.equals( PREEMPTIVE );
    }

    public boolean isNonPreemptive() {
        return this.equals( NON_PREEMPTIVE );
    }
}
