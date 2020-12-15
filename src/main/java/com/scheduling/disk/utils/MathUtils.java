package main.java.com.scheduling.disk.utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

public class MathUtils {

    @NotNull
    public static BigInteger getPositiveDifference(BigInteger number1, BigInteger number2  ) {
        return BigInteger.valueOf( Math.abs( number1.intValue() - number2.intValue() ) );
    }

    public static int findClosest(List<BigInteger> listOfNumbers, int indexOfClosest, BigInteger closestTo, boolean[] seen) {
        BigInteger minDifference = BigInteger.valueOf(Integer.MAX_VALUE);
        int closestIndex = 0;
        System.out.println("index of Closest: " + indexOfClosest + ", closest: " + listOfNumbers.get(indexOfClosest));
        System.out.println("seen[indexOfClosest]: " + seen[indexOfClosest]);
        for( int i = 0; i < listOfNumbers.size(); i++ ) {
            if( !seen[indexOfClosest] ) {
                BigInteger difference = getPositiveDifference( closestTo, listOfNumbers.get(i) );
                System.out.println("closestTo: " + closestTo + ", current: " + listOfNumbers.get(i) + ", difference: " + difference);
                if( difference.compareTo(minDifference) < 0 ) {
                    minDifference = difference;
                    closestIndex = i;
                }
            }
        }

        return closestIndex;
    }

    public static int findClosest(List<BigInteger> listOfNumbers, int indexOfClosest, BigInteger closestTo) {
        BigInteger minDifference = BigInteger.valueOf(Integer.MAX_VALUE);
        int closestIndex = 0;
        System.out.println("index of Closest: " + indexOfClosest + ", closest: " + listOfNumbers.get(indexOfClosest));
        for( int i = 0; i < listOfNumbers.size(); i++ ) {
            if( i != indexOfClosest ) {
                BigInteger difference = getPositiveDifference( closestTo, listOfNumbers.get(i) );
                System.out.println("closestTo: " + closestTo + ", current: " + listOfNumbers.get(i) + ", difference: " + difference);
                if( difference.compareTo(minDifference) < 0 ) {
                    minDifference = difference;
                    closestIndex = i;
                }
            }
        }

        return closestIndex;
    }

    public static int findClosest( List<BigInteger> listOfNumbers, BigInteger closestTo ) {
        BigInteger minDifference = BigInteger.valueOf(Integer.MAX_VALUE);
        int closestIndex = 0;
        for( int i = 0; i < listOfNumbers.size(); i++ ) {
            BigInteger difference = getPositiveDifference( closestTo, listOfNumbers.get(i) );
            if( difference.compareTo(minDifference) < 0 ) {
                minDifference = difference;
                closestIndex = i;
            }
        }
        return closestIndex;
    }
}
