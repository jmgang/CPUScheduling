package main.java.com.scheduling.exception;

public class WaitingTimeNotYetComputedException extends RuntimeException {

    public WaitingTimeNotYetComputedException() {
        super("Waiting Time not yet computed.");
    }
}
