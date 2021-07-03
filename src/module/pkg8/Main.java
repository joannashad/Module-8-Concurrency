/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author joann
 */
public class Main{

        public static void main(String args[]){ 
        int arrayLength=200000000;
        int myRandom = 0;
        int RandNumbers[]=new int[arrayLength];
         for (int i=0;i<RandNumbers.length;i++){
             myRandom = ThreadLocalRandom.current().nextInt(0,10);
             RandNumbers[i]=myRandom;
             }
        
        // We will store the threads so that we can check if they are done
        List<Thread> threads = new ArrayList<Thread>();
        for(int i=1;i<200000;i++){
            //process the sum for a range of 1000 random numbers
            Runnable task = new ThisThread(arrayLength,RandNumbers,(1000*i)-1000,1000*i);
            Thread worker = new Thread(task);
            worker.setName(String.valueOf(i));
            worker.start();            
            threads.add(worker);
            System.out.println(worker.getStackTrace());
        }
        //set the array length of 200M
        arrayLength=200000000;
        int RandNumbersAll[]=new int[arrayLength];
        myRandom = 0;
        //Create the random array
         for (int i=0;i<RandNumbersAll.length;i++){
             myRandom = ThreadLocalRandom.current().nextInt(0,10);
             RandNumbersAll[i]=myRandom;
             }
        //pass the array length, array, and the start/end of which index in the array to sum
        Runnable taskAll = new ThisThread(arrayLength,RandNumbersAll,0,arrayLength);
        Thread workerAll = new Thread(taskAll);
        workerAll.setName(String.valueOf(5));
        workerAll.start();
        threads.add(workerAll);
        
        
        int running = 0;
        do {
            running = 0;
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    running++;
                }
            }
            //System.out.println("We have " + running + " running threads. ");
        } while (running > 0);

        System.out.println("\nFinished all threads");
    }
}
class ThisThread implements Runnable{
    private  int arrayLength;
    Thread myThread;
    private int RandNumbers[];
    private int startN;
    private int endN;
    
    ThisThread(int arrayLength, int myArray[], int startN, int endN){
        this.arrayLength=arrayLength;
        this.RandNumbers=myArray;
        this.startN=startN;
        this.endN=endN;
    }
    
    @Override
    public void run(){  
         
        int arrayLength=this.arrayLength;
        int myRandom = 0;
              
        int mySum=0;
        try { 
            long startTime = System.nanoTime();
            //go through the array for the given start and end numbers
             for (int i=startN;i<endN;i++){
                 mySum+=RandNumbers[i];  
                 }
            // totalSum+=mySum;
            long endTime = System.nanoTime();
            long processTime = endTime-startTime;
            //procTime+=processTime;
            String results = "The sum of " + NumberFormat.getIntegerInstance().format(arrayLength)  + " random numbers \nbetween 1 and 10 [array " +  startN + " and " + endN + "] is " 
            + NumberFormat.getIntegerInstance().format(mySum) + " \nwhich took " + NumberFormat.getIntegerInstance().format(processTime)+ " nano seconds.\n";

            System.out.println(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }  
  
   public void start() {
        System.out.println("Thread started");
        if (myThread == null) {
         myThread = new Thread(this);
         myThread.start();
  }
 
 }
        

    
}
