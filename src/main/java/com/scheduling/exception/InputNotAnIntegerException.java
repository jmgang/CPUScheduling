package main.java.com.scheduling.exception;

public class InputNotAnIntegerException extends Exception {

    public InputNotAnIntegerException() {
        super("Input not valid. Should be a number.");
    }
}
