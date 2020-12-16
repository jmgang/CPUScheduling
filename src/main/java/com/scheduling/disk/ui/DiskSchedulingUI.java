package main.java.com.scheduling.disk.ui;

import main.java.com.scheduling.disk.base.DiskScheduler;
import main.java.com.scheduling.disk.scheduling.FirstComeFirstServeScheduler;
import main.java.com.scheduling.disk.scheduling.ScanDirection;
import main.java.com.scheduling.disk.scheduling.ScanScheduler;
import main.java.com.scheduling.disk.scheduling.ShortestSeekTimeFirstScheduler;
import main.java.com.scheduling.main.SchedulingApplication;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiskSchedulingUI {

    private DiskScheduler diskScheduler;

    private DiskSchedulingUI() {}

    public void showUI( Scanner sc ) {
        String currentPositionStr = "";
        String tracksizeStr = "";
        String seekRateStr = "";
        String numberOfRequestsStr = "";

        do {
            currentPositionStr = SchedulingApplication.input( "Input current position: ", sc );
        }while( !SchedulingApplication.isValidInteger(currentPositionStr) );

        do {
            tracksizeStr = SchedulingApplication.input("Input track size: ", sc);
        }while( !SchedulingApplication.isValidInteger(tracksizeStr) );

        do {
            seekRateStr = SchedulingApplication.input("Input seek rate: ", sc);
        }while( !SchedulingApplication.isValidInteger(seekRateStr) );

        do {
            numberOfRequestsStr = SchedulingApplication.input("Input number of request <max of 10 requests>: ", sc);
        }while( !SchedulingApplication.isWithinRange(numberOfRequestsStr, 1, 10) );

        int numberOfRequests = Integer.parseInt(numberOfRequestsStr);
        BigInteger trackSize = new BigInteger(tracksizeStr);
        List<BigInteger> requests = convertToBigIntegers(SchedulingApplication.loopInputWithRangeValidation("", "Loc", numberOfRequests,
                sc, 1, trackSize.intValue()-1));

        displayDiskSchedulingAlgorithmsMenu();
        runScheduler( new BigInteger(currentPositionStr), new BigInteger(seekRateStr), trackSize, requests, sc );
        displayOutput();
    }

    private void runScheduler( BigInteger currentPosition, BigInteger seekRate, BigInteger trackSize, List<BigInteger> requests, Scanner sc) {
        String choice = "";

        do {
            choice = SchedulingApplication.input("Enter choice: ", sc).trim().toUpperCase();
        }while( !SchedulingApplication.isChoiceInTheChoices( choice, "A", "B", "C", "D" ) );

        switch( choice ) {
            case "A":
                diskScheduler = new FirstComeFirstServeScheduler(currentPosition, requests, seekRate);
                break;
            case "B":
                diskScheduler = new ShortestSeekTimeFirstScheduler(currentPosition, requests, seekRate);
                break;
            case "C":
                ScanDirection scanDirection = ScanDirection.LEFT;
                String direction = "";
                do {
                    direction = SchedulingApplication.input(String.format("Input Direction: \n[A] Towards 0\n[B] Towards %d\nEnter choice: ",
                            trackSize.subtract(BigInteger.ONE)), sc);
                }while(!SchedulingApplication.isChoiceInTheChoices(direction, "A", "B"));

                if( "B".equals(direction.trim().toUpperCase()) ) {
                    scanDirection = ScanDirection.RIGHT;
                }
                diskScheduler = new ScanScheduler(currentPosition, requests, seekRate, trackSize, scanDirection);
                break;
            default:
                SchedulingApplication.exit();
        }

        diskScheduler.computeTotalHeadMovement();
    }

    private void displayOutput() {
        final StringBuilder output = new StringBuilder();
        output.append("Total head movement: ").append( diskScheduler.getTotalHeadMovement() )
                .append( "\nSeek Time: " ).append( diskScheduler.getSeekTime() ).append("\n");
        SchedulingApplication.pln(output.toString());
    }


    private List<BigInteger> convertToBigIntegers( List<Integer> inputs ) {
        List<BigInteger> convertedInputs = new ArrayList<>();

        inputs.forEach( input -> {
            convertedInputs.add( BigInteger.valueOf(input) );
        } );

        return convertedInputs;
    }


    private void displayDiskSchedulingAlgorithmsMenu() {
        final StringBuilder diskSchedulingMenu = new StringBuilder("Disk Scheduling Algorithm:\n");
        diskSchedulingMenu.append("[A] First Come First Serve (FCFS)\n").append("[B] Shortest Seek Time First (SSTF)\n")
                .append("[C] Scan\n").append("[D] Exit\n");
        SchedulingApplication.pln(diskSchedulingMenu.toString());
    }

    public static class Factory {
        private static DiskSchedulingUI diskSchedulingUI;

        public static DiskSchedulingUI newInstance() {
            return new DiskSchedulingUI();
        }
    }

}
