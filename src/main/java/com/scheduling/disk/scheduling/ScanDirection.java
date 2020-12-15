package main.java.com.scheduling.disk.scheduling;

public enum ScanDirection {
    LEFT, RIGHT;

    public boolean isLeft( ) {
        return this.equals( ScanDirection.LEFT );
    }
}
