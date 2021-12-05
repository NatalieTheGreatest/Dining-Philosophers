import java.util.Random;

//Dining Philosopher's problem
//Coded by Natalie Friede
//010892127

public class DiningPhilosopher {
    public static void main (String[] args){
        int runtime = 20;
        try{
            runtime = Integer.parseInt(args[0]);
            }
            catch(Exception e){
                //Mostly for if they give random numbers or not enough args. Not a big deal
                System.out.println("Integer args for runtime please");
            }
            //This is where a philosopher would go
            Restaurant oliveGarden = new Restaurant(5, runtime);
            Thread t = new Thread(oliveGarden);
            t.start();
        
    }

}

//Somewhat extraneous, but it's fun to keep with the theme of dining
class Restaurant implements Runnable 
{
private int philosophers, hours;

public Restaurant(int philosphers, int hours)
{
    this.philosophers = philosphers;
    this.hours = hours;
}
public Restaurant(){
    philosophers = 5;
    hours = 20;
}
    public void run()
    {
        Philosopher[] table = new Philosopher[this.philosophers];
        Chopstick[] chopsticks = new Chopstick[this.philosophers];
        for(int i = 0; i < this.philosophers; i++){
           chopsticks[i] = new Chopstick();
        }
        //Initializes the 4 normal philosophers
        for(int i = 0; i < this.philosophers - 1; i++){
            table[i] = new Philosopher(chopsticks[i], chopsticks[i+1]);
            Thread t = new Thread(table[i], "Philiosopher " + (i+1));
            t.start();
        }
        //The dyslexic philosopher
        //They don't know left/right and have the chopsticks switched in their mind
        //My friend Kira does this all the time
        //This eliminates deadlock since you cannot have circular waiting this way
        table[4] = new Philosopher(chopsticks[1], chopsticks[philosophers - 1]);
        Thread t = new Thread(table[philosophers - 1], "Philiosopher " + 5);
        t.start();
        try{
        Thread.sleep(hours*1000);
        System.out.println("Restraunt is closed. Philosophers are ejected");
        System.exit(0);
        }
        catch (Exception e){
            
        }
        
    }

}

class Philosopher implements Runnable {
    private final static Random generator = new Random();
    Chopstick leftChopstick, rightChopstick;
    public Philosopher(Chopstick leftChopstick, Chopstick rightChopstick)
    {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

     public void run() {
         try {
             while (true) {
                 System.out.println(Thread.currentThread().getName() + " is Thinking");
                 //The thoughts aren't that profound 
                 //They only think for <3 seconds
                 Thread.sleep(generator.nextInt(3000));
                 synchronized (leftChopstick) {
                    System.out.println(Thread.currentThread().getName() + " picked up left Chopstick");
                    //Left chopstick inspection just for more chaotic results
                    Thread.sleep(generator.nextInt(2000));
                     synchronized (rightChopstick) {
                         System.out.println(Thread.currentThread().getName() + " picked up right Chopstick");
                         //Fiddles with em or something for a random time
                         Thread.sleep(generator.nextInt(5000));
                         System.out.println(Thread.currentThread().getName() + " set down right Chopstick");
                     }
                     
                     // Back to thinking
                     System.out.println(Thread.currentThread().getName() + " set down left Chopstick");
                 }
             }
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
             return;
         }
     }
 }
 //Technically pointless but just for fun
 class Chopstick {
     private String woodType;
     public Chopstick(){
        //Objectively makes it SLIGHTLY less memory efficient but I think it's funny
        this.woodType = "balsam";
     }
 }