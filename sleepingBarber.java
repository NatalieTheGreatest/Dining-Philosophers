//Sleeping barber
//Developed mostly by Natalie Friede 010892127
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class sleepingBarber{

    // main is here
    public static void main (String[] args){
    // Input sleepTimeBarber and numChairs from command line
    int sleep = -1;
    int numChairs = -1;
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
    if(sleep == -1)
        sleep = 5;
    if(numChairs == -1)
        numChairs = 3;
    System.out.println("Nap time is " + sleep + ", and there are " + numChairs + " chairs");
    // Default sleepTimeBarber = 5, default numChairs = 3
    // Print parameters.
    // instantiate shop here.
    barberShop shop = new barberShop(numChairs);
    Barber barber = new Barber(shop, sleep);
    CustomerGenerator custGen = new CustomerGenerator(shop);
    Thread oneBarber = new Thread(barber);
    Thread multipleCustGen = new Thread(custGen);
    oneBarber.start();
    multipleCustGen.start();
    }
  }
    // Barber object that will become thread.
class Barber implements Runnable{
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
    System.out.println(new Date() + Thread.currentThread().getName());
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
        Thread.sleep(generator.nextInt(10000));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    }
    } 
}

class barberShop{
     LinkedList<Customer> customerList = new LinkedList<Customer>();
     int chairs = 0;
     Semaphore customers;
     Semaphore barber;
     Semaphore mutex;
     public barberShop(int chairs)
     {
         this.chairs = chairs;
         customers = new Semaphore(chairs);
         barber = new Semaphore(1);
         mutex = new Semaphore(1);

     }

    public void cutHair(){
    while(true){
       
        }
    // Wait on customer
    // Do things here like update number of customers waiting,
    //signal to wake up barber, etc.
    // Simulate cutting hair with sleep
    }
    public void add(Customer customer){
    // Wait on entering the critical section
    // DO THINGS HERE like determine if there are enough chairs
   // in the waiting room. Leave if waiting room is full. If waiting room is
    //not full, things must happen.
    }
}