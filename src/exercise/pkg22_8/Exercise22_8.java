/*
 *  Program:    Exercise 22.8
 *  Date:       4/1/18
 *  Developer:  Garrett Wolfe
 *  Purpose:    Write a program that finds
 *              all prime numbers up to 10,000,000,000 . There are approximately
 *              455,052,511 such prime numbers.
 */
package exercise.pkg22_8;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Exercise22_8 {
    public static void main(String[] args) throws IOException {
        long n = 10_000_000_000L;  
        RandomAccessFile input = new RandomAccessFile("PrimeNumbers.dat", "rw");
        java.util.List<Long> list = new java.util.ArrayList<>();
        long count = 0; 
        long number = 2; 
        int squareRoot = 1; 
        if(input.length() > 0) {
            input.seek(input.length() - 8);
            number = input.readLong() + 1;
            input.seek(0);
            try {
                for (int i = 0; i < 10000; i++) {
                    list.add(input.readLong());
                }   
            } catch (EOFException e) {
            } 
            squareRoot = (int)(Math.sqrt(number)) + 1;
            count = input.length() / 8;
        } 
        while (number <= n) {
            boolean isPrime = true;
            if (squareRoot * squareRoot < number)
                squareRoot++;
            while(true) {
                isPrime = true;
                int k;
                for (k = 0; k < list.size() && list.get(k) <= squareRoot; k++) {
                    if (number % list.get(k) == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (input.getFilePointer() == input.length()) {
                    break;
                }    
                if (!isPrime) {
                    break;
                } else  {
                    list.clear();
                    try {
                        for (int i = 0; i < 10000; i++) {
                            list.add(input.readLong());
                        }   
                    } catch (EOFException e) {
                }
                }
            }
            if (isPrime) {
                count++;
                System.out.println(count + "\t" + number + "\t" + list.size());
                input.writeLong(number);
                list.clear();
                input.seek(0);
                try {
                    for (int i = 0; i < 10000; i++) {
                        list.add(input.readLong());
                    }   
                } catch (EOFException e) {
                } 
            }
            number++;
        }
        System.out.println("\n" + count + " prime(s) less than or equal to " + n);
        input.close();
    }
}
