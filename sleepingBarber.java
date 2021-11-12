//Sleeping barber
//Developed mostly by Natalie Friede 010892127
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public class sleepingBarber{

    // main is here
    public static void main (String[] args){
    // Input sleepTimeBarber and numChairs from command line
    int sleep = 5;
    int numChairs = 3;
    try{
    sleep = Integer.parseInt(args[0]);
    }
    catch(Exception e){
        //Mostly for if they give random numbers or not enough args. Not a big deal
        System.out.println("Integer args please");
    }
    try{
        numChairs = Integer.parseInt(args[1]);
    }
    catch(Exception e){
         System.out.println("Integer args please");
    }
    //Let the guy rest! No 0 sleep and always has a waiting room
    if(sleep <= 0)
        sleep = 5;
    if(numChairs <= 0)
        numChairs = 3;
    System.out.println("Nap time is " + sleep + ", and there are " + numChairs + " chairs");
    // Default sleepTimeBarber = 5, default numChairs = 3
    // Print parameters.
    // instantiate shop here.
    barberShop shop = new barberShop(numChairs, sleep);
    Barber barber = new Barber(shop, sleep);
    CustomerGenerator custGen = new CustomerGenerator(shop);
    Thread oneBarber = new Thread(barber);
    Thread multipleCustGen = new Thread(custGen);
    oneBarber.start();
    multipleCustGen.start();
    }
  }
    // Barber object that will become thread.
class Barber implements Runnable {
    barberShop shop;
    int sleepTime;
    // Need access to shop object.
    public Barber(barberShop shop, int sleep){
    this.shop = shop;
    this.sleepTime = sleep;
    }
    public void run(){
    //Simulate sleep by putting thread to sleep
    while(true){
    shop.cutHair();
    }
  }
}
    // Customer object that will become thread.
class Customer implements Runnable{
    barberShop shop;
    // Need access to shop object.
    public Customer(barberShop shop){
    this.shop = shop;
    }
    public void run(){
    goForHairCut();
    }
    private void goForHairCut(){
    shop.add(this);
    }
}
    // CustomerGenerator that will become thread to start customer
   // threads.
class CustomerGenerator implements Runnable{
    private final static Random generator = new Random();
    barberShop shop;
    // Need access to shop object.
    public CustomerGenerator(barberShop shop){
    this.shop = shop;
    }
    public void run(){
    while(true){
    // Create customers and pass object “shop”
    Customer c = new Customer(shop);
    // Create thread
    Thread cutsomerThread = new Thread(c);
    // start threads
    cutsomerThread.start();
    // sleep random amount of time
    try {
        Thread.sleep(generator.nextInt(3000));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
   }
  } 
}

class barberShop {
     private final static Random generator = new Random();
     BlockingQueue<Customer> customerList;
     int chairs = 0;
     int sleepTime = 0;
     Semaphore mutex;
     Semaphore occupiedSeats;
     //The mutexes
     //for barber
     Semaphore barberReady;
     //for customer

     public barberShop(int chairs, int sleepTime)
     {
         this.chairs = chairs;
         occupiedSeats = new Semaphore(chairs);
         mutex = new Semaphore(1);
         customerList = new ArrayBlockingQueue<Customer>(chairs);
         this.sleepTime = sleepTime;
     }

    public void cutHair(){
    while(true){
        if(!customerList.isEmpty())
        {
        //If he isn't asleep he falls asleep
        if(barberReady.tryAcquire()){
          System.out.println("Barber has fallen asleep");
          try{
            barberReady.release();
             }
            catch(Exception e)
            {
               System.out.println("Barber had a nightmare");
            }
        }
        //If he was asleep, he sleeps 
        else {
            try {
                //It really should be released, but double releasing it just in case
                barberReady.release();
                Thread.sleep(sleepTime*1000);
            } catch (InterruptedException e) {
                System.out.println("Barber can't catch a break and is insomniac");
            }
        }
        }
        //If barber is ready he's gonna do stuff
        else if(barberReady.tryAcquire())
        {
            Customer c = customerList.poll();

        }
        
        }
    // Wait on customer
    // Do things here like update number of customers waiting,
    //signal to wake up barber, etc.
    // Simulate cutting hair with sleep
    }
    public void add(Customer customer){
     System.out.println("Customer " + Thread.currentThread().getName() + " enters the shop at " + new Date());
     try{
        if(occupiedSeats.tryAcquire())
        {
            System.out.println("Customer " + Thread.currentThread().getName() + " gets a chair");
            customerList.add(customer);
            try {
               if(barberReady.availablePermits() < 1){
                    barberReady.release();
                   System.out.println("Barber is being woken");
               }
            }
            catch(Exception e){
                System.out.println("The barber shop had a mixup! Oopsies");
            }
        }
           
        else{
            System.out.println("Customer " + Thread.currentThread().getName() + " does not get a seat and leaves the shop"); 
            Thread.currentThread().interrupt();
        }
     }
     catch(Exception e)
     {
         System.out.println(e.getStackTrace());
     }

    }
}