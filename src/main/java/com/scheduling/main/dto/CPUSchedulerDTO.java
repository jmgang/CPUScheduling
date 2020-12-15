package main.java.com.scheduling.main.dto;

import main.java.com.scheduling.exception.InputNotAnIntegerException;
import main.java.com.scheduling.main.validator.Validator;

public class CPUSchedulerDTO {

    private int numberOfRequests;

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(String numberOfRequests) throws InputNotAnIntegerException {
        Validator.validateStringToInt(numberOfRequests);
        this.numberOfRequests = Integer.parseInt(numberOfRequests.trim());
    }


}
