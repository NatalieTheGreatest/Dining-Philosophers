This is a solution to the dining Philosophers problem
In this there are three objects
The Restraunt which houses the philosophers,
The philosophers which handle their chopsticks
And chopsticks which are pointless, but could do things in the future like break 
(I am awake I could have just used an Object instead of chopsticks)
The restraunt takes in its hours, which determine how long the program will run and the number of philosophers
A table is made with that many philosophers and chopsticks (horrible service, but this isn't a real restraunt)
The philosophers all try to take their left fork 
If this were the case, there could be a circular wait condition, but one of the philosophers rejects the idea of left and right and reaches for his "right" fork first
Since he cannot get the left without the right, there will never be permanant deadlock, since he won't reach for the left without the right
Worst case scenario everyone has one chopstick except this guy who has 2 and one who has 0, but that would reselove itself eventually and isn't deadlocked
They pick up and set down chopsticks until the restraunt closes, then they get ejected