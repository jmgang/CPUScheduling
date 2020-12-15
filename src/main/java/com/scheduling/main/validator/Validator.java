package main.java.com.scheduling.main.validator;

import main.java.com.scheduling.exception.InputNotAnIntegerException;

public class Validator {

    public static void validateStringToInt( String number ) throws InputNotAnIntegerException {
        try{
            Integer.parseInt( number.trim() );
        }catch (NumberFormatException nfe) {
            throw new InputNotAnIntegerException();
        }

    }
}
