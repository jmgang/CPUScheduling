package main.java.com.scheduling.cpu.process;

import java.math.BigInteger;

public abstract class AbstractProcess {

    private static BigInteger counter = BigInteger.ZERO;

    private final BigInteger processId;

    protected AbstractProcess(){
        counter = counter.add( BigInteger.ONE );
        this.processId = counter;
    }

    public BigInteger getProcessId() {
        return processId;
    }



}
