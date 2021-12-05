import java.util.Random;

//Dining Philosopher's problem
//Coded by Natalie Friede
//010892127

public class DiningPhilosopher {
    public static void main (String[] args){
        //Table stores philosophers
        Philosopher[] table = new Philosopher[5];
        Chopstick[] chopsticks = new Chopstick[5];
        for(int i = 0; i < 5; i++){
           chopsticks[i] = new Chopstick();
        }
        //Initializes the 4 normal philosophers
        for(int i = 0; i < 4; i++){
            table[i] = new Philosopher(chopsticks[i], chopsticks[i+1]);
            Thread t = new Thread(table[i], "Philiosopher " + (i+1));
            t.start();
        }
        //The dyslexic philosopher
        //They don't know left/right and have the chopsticks switched in their mind
        //My friend Kira does this all the time
        //This eliminates deadlock since you cannot have circular waiting this way
        table[4] = new Philosopher(chopsticks[1], chopsticks[4]);
        Thread t = new Thread(table[4], "Philiosopher " + 5);
        t.start();
        
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