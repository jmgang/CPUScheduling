package main.java.com.scheduling.main;

import main.java.com.scheduling.cpu.base.CPUScheduler;
import main.java.com.scheduling.cpu.nonpreemptive.FirstComeFirstServeCPUScheduler;
import main.java.com.scheduling.cpu.nonpreemptive.NonPreemptivePriorityCPUScheduler;
import main.java.com.scheduling.cpu.nonpreemptive.ShortestJobFirstCPUScheduler;
import main.java.com.scheduling.cpu.preemptive.PreemptivePriorityCPUScheduler;
import main.java.com.scheduling.cpu.preemptive.RoundRobinCPUScheduler;
import main.java.com.scheduling.cpu.preemptive.ShortestRemainingTimeFirstCPUScheduler;
import main.java.com.scheduling.cpu.process.ExtendedProcess;
import main.java.com.scheduling.disk.ui.DiskSchedulingUI;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public class SchedulingApplication {

    private static boolean isRunning = true;

    public void run() {
        Scanner sc = new Scanner(System.in);
        do {
            run(sc);
            askToRerun(sc);
        }while( isRunning );
    }

    public void askToRerun(Scanner sc) {
        String choice = input("Input again (y/n)?\n", sc).trim().toLowerCase();
        if( "n".equals(choice) ) {
            isRunning = false;
        }else if( !"y".equals(choice) ) {
            pln("Sorry, cannot understand \"" + choice + "\". Please input again.");
            askToRerun(sc);
        }
    }

    public void runCPUScheduling(Scanner sc, SchedulerType schedulerType) {
        CPUScheduler scheduler = null;
        String numberOfProcessesStr = "";
        do {
            numberOfProcessesStr = input("Input no. of processes [2-9]: ", sc);
        }while( !isWithinRange( numberOfProcessesStr, 2, 9 ) );


        int numberOfProcesses = Integer.parseInt(numberOfProcessesStr);
        List<ExtendedProcess> processes = new ArrayList<>();
        List<Integer> arrivalTimes = loopInput("Input individual arrival time: ", "AT", numberOfProcesses, sc);
        List<Integer> burstTimes = loopInput("Input individual burst time: ", "BT", numberOfProcesses, sc);

        for( int i = 0; i < numberOfProcesses; i++ ) {
            processes.add( new ExtendedProcess( burstTimes.get(i), arrivalTimes.get(i) ) );
        }

        if( schedulerType.isPreemptive() ) {
            scheduler = displayPreemptiveCPUSchedulingMenu(processes, sc);
        }else {
            scheduler = displayNonPreemptiveCPUSchedulingMenu(processes, sc);
        }

        scheduler.computeSchedulingTimes();
        displayCPUSchedulingOutput(scheduler);
    }

    public static boolean isValidInteger( final String input ) {
        try {
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException nfe) {
            pln("Wrong input. Please input a number.");
            return false;
        }
    }

    public static boolean isWithinRange( final String input, int low, int high ) {
        if(!isValidInteger(input)) return false;
        int in = Integer.parseInt(input);
        if( low <= in && in <= high ) return true;
        else {
            pln(String.format("Input not within range. Should be from %d to %d", low, high));
            return false;
        }
    }

    public void run( Scanner sc ) {
        final StringBuilder menu = new StringBuilder("Choose Scheduling type: \n");
        menu.append("[A] Preemptive CPU Scheduling\n[B] Non-Preemptive CPU Scheduling\n[C] Disk Scheduling\n[D] Exit\n");
        pln(menu.toString());
        String choice = "";
        do {
            choice = input("Enter choice: ", sc);
        }while( !isChoiceInTheChoices(choice, "A", "B", "C", "D") );

        SchedulerType schedulerType = null;
        switch (choice) {
            case "A":
                schedulerType = SchedulerType.PREEMPTIVE;
                break;
            case "B":
                schedulerType = SchedulerType.NON_PREEMPTIVE;
                break;
            case "C":
                break;
            default: //implicit D
                exit();
        }

        if(Objects.nonNull(schedulerType)) {
            runCPUScheduling(sc, schedulerType);
        }else{
            runDiskScheduling(sc);
        }

    }

    public void runDiskScheduling(Scanner sc) {
        DiskSchedulingUI ui = DiskSchedulingUI.Factory.newInstance();
        ui.showUI(sc);
    }

    public CPUScheduler displayNonPreemptiveCPUSchedulingMenu(List<ExtendedProcess> processes, Scanner sc) {
        CPUScheduler scheduler = null;
        displayCPUSchedulingAlgorithmsMenu( SchedulerType.NON_PREEMPTIVE );
        String choice = "";

        do {
            choice = input("Enter choice: ", sc);
        }while( !isChoiceInTheChoices(choice, "A", "B", "C", "D") );

        switch(choice.trim()) {
            case "A":
                scheduler = new FirstComeFirstServeCPUScheduler(toArray(processes));
                break;
            case "B":
                scheduler = new ShortestJobFirstCPUScheduler(toArray(processes));
                break;
            case "C":
                List<Integer> priorities = loopInput("Input individual priority number (lowest is higher): ", "Prio", processes.size(), sc);
                IntStream.range(0, processes.size()).forEach( index -> processes.get(index).setPriority( priorities.get(index) ) );
                scheduler = new NonPreemptivePriorityCPUScheduler(toArray(processes));
                break;
            default:
                //exit, terminate
                exit();
        }
        return scheduler;
    }

    public CPUScheduler displayPreemptiveCPUSchedulingMenu(List<ExtendedProcess> processes, Scanner sc) {
        CPUScheduler scheduler = null;
        displayCPUSchedulingAlgorithmsMenu( SchedulerType.PREEMPTIVE );
        String choice = "";

        do {
            choice = input("Enter choice: ", sc);
        }while( !isChoiceInTheChoices(choice, "A", "B", "C", "D") );

        switch(choice.trim()) {
            case "A":
                scheduler = new ShortestRemainingTimeFirstCPUScheduler(toArray(processes));
                break;
            case "B":
                String timeSlice = "";
                do{
                    timeSlice = input("Input time slice: ", sc);
                }while(!isValidInteger(timeSlice));

                BigInteger quantum = BigInteger.valueOf(Integer.parseInt( timeSlice ));
                scheduler = new RoundRobinCPUScheduler(toArray(processes), quantum);
                break;
            case "C":
                List<Integer> priorities = loopInput("Input individual priority number (lowest is higher): ", "Prio", processes.size(), sc);
                IntStream.range(0, processes.size()).forEach( index -> processes.get(index).setPriority( priorities.get(index) ) );
                scheduler = new PreemptivePriorityCPUScheduler(toArray(processes));
                break;
            default:
                //exit, terminate
                System.exit(0);

        }

        return scheduler;

    }

    public static boolean isChoiceInTheChoices( final String choice, String... otherChoices ) {
        for( String otherChoice : otherChoices ) {
            if( choice.equalsIgnoreCase( otherChoice ) ) return true;
        }

        pln("Your choice of \"" + choice + "\" is not found. Please choose a valid choice.");

        return false;
    }

    public ExtendedProcess[] toArray( List<ExtendedProcess> processes ) {
        return processes.toArray( new ExtendedProcess[processes.size()] );
    }

    public void displayCPUSchedulingOutput(CPUScheduler scheduler) {
        int[] turnAroundTimes = scheduler.getTurnAroundTimes();
        int[] waitingTimes = scheduler.getWaitingTimes();

        pln(String.format("%s %41s:", "Waiting time", "Turnaround time"));

        for(int i = 0; i < turnAroundTimes.length; i++) {
            String param = ("P" + (i+1));
            pln(String.format( "%s: %d %35s: %d", param, waitingTimes[i], param, turnAroundTimes[i] ));
        }

        pln(String.format("%s: %.2f %35s: %.2f", "Average Waiting Time", scheduler.getAverageWaitingTime(),
                            "Average Turnaround Time", scheduler.getAverageTurnAroundTime()));
    }

    public static String input(final String prompt, Scanner sc )  {
        p(prompt);
        return sc.nextLine();
    }

    public static String input(final String prompt, Scanner sc, Class<?> klass) {
        p(prompt);
        return sc.nextLine();
    }

    public void displayDiskSchedulingAlgorithmsMenu() {
        final StringBuilder diskMenu = new StringBuilder("Disk Scheduling Algorithms: \n");
        diskMenu.append("[A] First Come First Serve (FCFS)\n").append("[B] Shortest Seek Time First (SSTF)\n")
                .append("[C] Scan\n").append("[D] Exit\n");

        pln(diskMenu.toString());
    }

    public void displayCPUSchedulingAlgorithmsMenu(SchedulerType schedulerType) {
        final StringBuilder schedulingMenu = new StringBuilder("CPU Scheduling Algorithms: \n");
        //preemptive
        if( schedulerType.isPreemptive() ) {
            schedulingMenu.append( "[A] Shortest Remaining Time First (SRTF)\n" )
                    .append("[B] Round Robin (RR)\n")
                    .append("[C] Preemptive Priority (P-Prio)\n");

        }else{
            schedulingMenu.append("[A] First Come First Serve (FCFS)\n")
                    .append("[B] Shortest Job First (SJF)\n")
                    .append("[C] Non Preemptive Priority (Non-P Prio) \n");
        }

        schedulingMenu.append("[D] Exit\n");
        p(schedulingMenu.toString());
    }

    public static List<Integer> loopInput(final String promptMessage, final String promptMessageElement, final int totalNumberOfInputs, Scanner sc) {
        List<Integer> inputs = new ArrayList<>();

        if( Objects.nonNull(promptMessage) && !promptMessage.isEmpty() ) {
            pln(promptMessage);
        }

        for( int i = 0; i < totalNumberOfInputs; i++ ) {
            String inputString = "";
            do {
                inputString = input(promptMessageElement + (i+1) + ": ", sc);
            }while( !isValidInteger(inputString) );

            inputs.add( Integer.parseInt( inputString ) );
        }

        return inputs;
    }

    public static List<Integer> loopInputWithRangeValidation(final String promptMessage, final String promptMessageElement, final int totalNumberOfInputs, Scanner sc,
                                                             int low, int high) {
        List<Integer> inputs = new ArrayList<>();

        if( Objects.nonNull(promptMessage) && !promptMessage.isEmpty() ) {
            pln(promptMessage);
        }

        for( int i = 0; i < totalNumberOfInputs; i++ ) {
            String inputString = "";
            do {
                inputString = input(promptMessageElement + (i+1) + ": ", sc);
            }while( !isWithinRange(inputString, low, high) );

            inputs.add( Integer.parseInt( inputString ) );
        }

        return inputs;
    }



    // I know it's bad practice; but wth I'm just lazy as fuck
    public static void pln(final String message) {
        System.out.println(message);
    }

    public static void p(final String message) {
        System.out.print(message);
    }

    public static void exit() {
        System.exit(0);
    }
}
