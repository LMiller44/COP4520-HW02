import java.util.concurrent.locks.ReentrantLock;

class MinotuarVase {

    static final int totalGuests = 50;
    static boolean roomAvailable = true;
    static int count = 0;

    static boolean observedVase[] = new boolean[totalGuests];

    private static ReentrantLock mutex = new ReentrantLock();

    // random number generator used to randomly select a guest
    static int chooseGuest () {
        return (int) (Math.random() * (totalGuests - 0) + 0);
    }

    static void lookAtVase(int guestId) {
        while (count < totalGuests) {
            mutex.lock();

            if (observedVase[guestId] == false && roomAvailable == true) {
                roomAvailable = false;
                observedVase[guestId] = true;
                count++;
                int wait = viewTime();
                
                // guest views the vase for a random amount of time.
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                }             

                System.out.println("Guest "+guestId+" looked at the vase for "+wait+" milliseconds.");
                roomAvailable = true;
            }
            
            mutex.unlock();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
        }
    }

    // random number generator used to randomly select a guest
    static int viewTime () {
        return (int) (Math.random() * (100 - 50) + 50);
    }

    public static void main (String[] args) {

        VaseGuest guestList[] = new VaseGuest[totalGuests];
        for (int i = 0; i < totalGuests; i++) {
            guestList[i] = new VaseGuest(i);
            guestList[i].start();
        }

        // waits for all threads to finish
        for (int j = 0; j < totalGuests; j++) {
            try {
                guestList[j].join();
            } catch (InterruptedException e) {
            }  
        }

        System.out.println("\nAll guests have taken time to look at the vase.");
    }
}