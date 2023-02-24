import java.util.concurrent.locks.ReentrantLock;

class MinotuarMaze {

    static final int totalGuests = 50;

    // tracks which guests have been fed
    static boolean fedGuests[] = new boolean[totalGuests];
    static boolean availableCupcake = true;
    static int count = 0;

    private static ReentrantLock mutex = new ReentrantLock();

    // random number generator used to randomly select a guest
    static int chooseGuest () {
        return (int) (Math.random() * (totalGuests - 0) + 0);
    }

    static void enterMaze(int guestId) {
        while (count < totalGuests) {
            int chosen = chooseGuest();
            mutex.lock();

            // only allows the guestId that was randomly chosen to enter the maze
            if (guestId == chosen) {
                if (fedGuests[guestId] == false && availableCupcake == true) {
                    fedGuests[guestId] = true;
                    availableCupcake = false;
                    count++;
                    System.out.println("Guest "+guestId+" found a cupcake at the end of the maze!");
                    System.out.println("They eat it and are now full.\n");
                }
    
                else if (fedGuests[guestId] == false && availableCupcake == false) {
                    System.out.println("Guest "+guestId+" reached the end of the maze, but there is no cupcake. They request another.");
                    System.out.println("Guest "+guestId+" ate their cupcake, and are now full.\n");
                    fedGuests[guestId] = true;
                    count++;
                }

                else if (fedGuests[guestId] == true && availableCupcake == true) {
                    System.out.println("Guest "+guestId+" finds a cupcake at the end of the maze, but has already eaten. They leave the cupcake.\n");
                }

                else if (fedGuests[guestId] == true && availableCupcake == false) {
                    System.out.println("Guest "+guestId+" reaches the end of the maze and requests a cupcake for the next guest.\n");
                    availableCupcake = true;
                }
            }

            mutex.unlock();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }            
        }
    }

    public static void main(String[] args) {

        int firstGuest;
        Guest guestList[] = new Guest[totalGuests];

        // randomly selects first guest and lets them enter the maze.
        firstGuest = chooseGuest();
        guestList[firstGuest] = new Guest(firstGuest);
        System.out.println("Guest "+firstGuest+" was the first selected guest! They must now stand at the exit and count how many cupcakes are eaten.");
        System.out.println("Guest "+firstGuest+" found a cupcake at the end of the maze!");
        System.out.println("They eat it and are now full.\n");
        fedGuests[firstGuest] = true;
        availableCupcake = false;
        count++;

        for (int i = 0; i < totalGuests; i++) {
            if (i != firstGuest) {
                guestList[i] = new Guest(i);
                guestList[i].start();
            }
        }

        // waits for all threads to finish
        for (int j = 0; j < totalGuests; j++) {
            try {
                guestList[j].join();
            } catch (InterruptedException e) {
            }  
        }

        System.out.println("Guest "+firstGuest+" informs the minotuar that all "+totalGuests+" guests have completed the maze.");
    }
}