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
        String currentPositionStr = SchedulingApplication.input( "Input current position: ", sc );
        String tracksizeStr = SchedulingApplication.input("Input track size: ", sc);
        String seekRateStr = SchedulingApplication.input("Input seek rate: ", sc);
        String numberOfRequestsStr = SchedulingApplication.input("Input number of request: ", sc);

        validateInputs(currentPositionStr, tracksizeStr, seekRateStr, numberOfRequestsStr);

        int numberOfRequests = Integer.parseInt(numberOfRequestsStr);
        List<BigInteger> requests = convertToBigIntegers(SchedulingApplication.loopInput("", "Loc", numberOfRequests, sc));

        BigInteger trackSize = new BigInteger(tracksizeStr);
        validateRequests( requests, trackSize );

        displayDiskSchedulingAlgorithmsMenu();
        runScheduler( new BigInteger(currentPositionStr), new BigInteger(seekRateStr), trackSize, requests, sc );
        displayOutput();
    }

    private void runScheduler( BigInteger currentPosition, BigInteger seekRate, BigInteger trackSize, List<BigInteger> requests, Scanner sc) {
        String choice = SchedulingApplication.input("Enter choice: ", sc).trim().toUpperCase();
        switch( choice ) {
            case "A":
                diskScheduler = new FirstComeFirstServeScheduler(currentPosition, requests, seekRate);
                break;
            case "B":
                diskScheduler = new ShortestSeekTimeFirstScheduler(currentPosition, requests, seekRate);
                break;
            case "C":
                ScanDirection scanDirection = ScanDirection.LEFT;
                String direction = SchedulingApplication.input(String.format("Input Direction: \n[A] Towards 0\n[B] Towards %d\nEnter choice: ", trackSize.subtract(BigInteger.ONE)), sc);
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

    private void validateInputs(String currentPosition, String trackSize, String seekRate, String numberOfRequests) {

    }

    private void validateRequests( List<BigInteger> requests, BigInteger trackSize ) {

    }

    private void displayDiskSchedulingAlgorithmsMenu() {
        final StringBuilder diskSchedulingMenu = new StringBuilder("Disk Scheduling Algorithm:\n");
        diskSchedulingMenu.append("[A] First Come First Serve (FCFS)\n").append("[B] Shortest Seek Time First (SSTF)\n")
                .append("[C] Scan\n");
        SchedulingApplication.pln(diskSchedulingMenu.toString());
    }

    public static class Factory {
        private static DiskSchedulingUI diskSchedulingUI;

        public static DiskSchedulingUI newInstance() {
            return new DiskSchedulingUI();
        }
    }

}
