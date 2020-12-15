package main.java.com.scheduling.main.dto;

import main.java.com.scheduling.exception.InputNotAnIntegerException;
import main.java.com.scheduling.main.validator.Validator;

public class ProcessDTO {

    private int arrivalTime;

    private int burstTime;

    public ProcessDTO() {
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) throws InputNotAnIntegerException {
        Validator.validateStringToInt(arrivalTime);
        this.arrivalTime = Integer.parseInt(arrivalTime.trim());
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(String burstTime) throws InputNotAnIntegerException {
        Validator.validateStringToInt(burstTime);
        this.burstTime = Integer.parseInt(burstTime.trim());
    }
}
