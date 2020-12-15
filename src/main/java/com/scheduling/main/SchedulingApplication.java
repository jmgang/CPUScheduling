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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
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
        // preemptive
        int numberOfProcesses = Integer.parseInt(input("Input no. of processes [2-9]: ", sc));
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

    public void run( Scanner sc ) {
        final StringBuilder menu = new StringBuilder("Choose Scheduling type: \n");
        menu.append("[A] Preemptive CPU Scheduling\n[B] Non-Preemptive CPU Scheduling\n[C] Disk Scheduling\n[D] Exit\n");
        pln(menu.toString());
        final String choice = input("Enter choice: ", sc).trim();

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
            default:
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
        String choice = input("Enter choice: ", sc);

        // TODO assuming preemptive
        // TODO REFACTOR ROUND ROBIN - WRONG? should be sorted?
        switch(choice.trim()) {
            case "A":
                scheduler = new FirstComeFirstServeCPUScheduler(toArray(processes));
                break;
            case "B":
                scheduler = new ShortestJobFirstCPUScheduler(toArray(processes));
                break;
            case "C":
                List<Integer> priorities = loopInput("Input individual priority number: ", "Prio", processes.size(), sc);
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
        // TODO prompt for preemptive or non
        displayCPUSchedulingAlgorithmsMenu( SchedulerType.PREEMPTIVE );
        String choice = input("Enter choice: ", sc);

        switch(choice.trim()) {
            case "A":
                scheduler = new ShortestRemainingTimeFirstCPUScheduler(toArray(processes));
                break;
            case "B":
                String timeSlice = input("Input time slice: ", sc);
                BigInteger quantum = BigInteger.valueOf(Integer.parseInt( timeSlice ));
                scheduler = new RoundRobinCPUScheduler(toArray(processes), quantum);
                break;
            case "C":
                List<Integer> priorities = loopInput("Input individual priority number: ", "Prio", processes.size(), sc);
                IntStream.range(0, processes.size()).forEach( index -> processes.get(index).setPriority( priorities.get(index) ) );
                scheduler = new PreemptivePriorityCPUScheduler(toArray(processes));
                break;
            default:
                //exit, terminate
                System.exit(0);

        }

        System.out.println("is here");

        return scheduler;

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
        //TODO
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
                    .append("[B] Non Preemptive Priority (Non-P Prio)\n")
                    .append("[C] Shortest Job First (SJF)\n");
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
            inputs.add( Integer.parseInt(input(promptMessageElement + (i+1) + ": ", sc)) );
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
